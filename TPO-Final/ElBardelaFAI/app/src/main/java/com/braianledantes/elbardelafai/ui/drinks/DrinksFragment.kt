package com.braianledantes.elbardelafai.ui.drinks

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.braianledantes.elbardelafai.App
import com.braianledantes.elbardelafai.R
import com.braianledantes.elbardelafai.databinding.FragmentDrinksBinding
import com.braianledantes.elbardelafai.repository.DrinksRepository
import com.braianledantes.elbardelafai.util.hideKeyboard
import com.braianledantes.elbardelafai.util.onQueryTextChanged
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DrinksFragment : Fragment(), MenuProvider {

    private var _binding: FragmentDrinksBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DrinksViewModel by activityViewModels {
        val application = (activity?.application as App)
        val database = application.database
        DrinksViewModelFactory(
            DrinksRepository(database = database)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrinksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val drinksAdapter = DrinksAdapter { drink ->
            val action = DrinksFragmentDirections.actionNavigationDrinksToDrinkFragment(
                drinkId = drink.id,
                drinkName = drink.name
            )
            findNavController().navigate(action)
        }
        binding.drinkList.apply {
            adapter = drinksAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy != 0) binding.drinkList.hideKeyboard(requireContext())
                }
            })
        }

        lifecycleScope.launch {
            viewModel.pagingDrinks.collectLatest { pagingData ->
                drinksAdapter.submitData(pagingData)
            }
        }

        binding.btnCreateDrink.setOnClickListener {
            val action = DrinksFragmentDirections.actionNavigationDrinksToCreateDrinkFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.drinks_menu, menu)

        val searchView = menu.findItem(R.id.menu_item_search).actionView as SearchView
        searchView.queryHint = getString(R.string.search_drink)
        searchView.onQueryTextChanged { query ->
            searchDrink(query ?: "")
        }
        searchView.setOnCloseListener {
            searchDrink("")
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

    private fun searchDrink(query: String) {
        binding.drinkList.scrollToPosition(0)
        viewModel.searchDrinksByName(query)
    }

}