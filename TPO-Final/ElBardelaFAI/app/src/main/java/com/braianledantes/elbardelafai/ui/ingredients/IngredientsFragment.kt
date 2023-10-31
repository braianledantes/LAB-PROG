package com.braianledantes.elbardelafai.ui.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.braianledantes.elbardelafai.App
import com.braianledantes.elbardelafai.R
import com.braianledantes.elbardelafai.databinding.FragmentIngredientsBinding
import com.braianledantes.elbardelafai.repository.IngredientsRepository
import com.braianledantes.elbardelafai.util.hideKeyboard
import com.braianledantes.elbardelafai.util.onQueryTextChanged
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class IngredientsFragment : Fragment(), MenuProvider {

    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: IngredientsViewModel by activityViewModels {
        val application = (activity?.application as App)
        IngredientsViewModelFactory(
            IngredientsRepository(database = application.database)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val ingredientsAdapter = IngredientsAdapter()
        binding.ingredientList.apply {
            adapter = ingredientsAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy != 0) binding.root.hideKeyboard(requireContext())
                }
            })
        }

        lifecycleScope.launch {
            viewModel.pagingIngredients.collectLatest { pagingIngredients ->
                ingredientsAdapter.submitData(pagingIngredients)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.ingredients_menu, menu)

        val searchView = menu.findItem(R.id.menu_item_search).actionView as SearchView
        searchView.queryHint = getString(R.string.search_ingredient)
        searchView.onQueryTextChanged { query ->
            searchIngredient(query ?: "")
        }
        searchView.setOnCloseListener {
            searchIngredient("")
            false
        }
        lifecycleScope.launch {
            viewModel.search.collectLatest {
                if (it.isNotBlank()) {
                    searchView.setQuery(it, false)
                    searchView.isIconified = false
                }
            }
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_item_search -> true
            else -> false
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.addMenuProvider(this)
    }

    override fun onStop() {
        super.onStop()
        activity?.removeMenuProvider(this)
    }

    private fun searchIngredient(query: String) {
        binding.ingredientList.scrollToPosition(0)
        viewModel.searchIngredientsByName(query)
    }
}