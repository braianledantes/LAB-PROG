package com.braianledantes.elbardelafai.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.braianledantes.elbardelafai.App
import com.braianledantes.elbardelafai.R
import com.braianledantes.elbardelafai.databinding.FragmentHomeBinding
import com.braianledantes.elbardelafai.repository.PopularDrinksRepository
import com.braianledantes.elbardelafai.ui.about.AboutActivity

class HomeFragment : Fragment(), MenuProvider {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        val application = activity?.application as App
        val database = application.database
        HomeViewModelFactory(
            PopularDrinksRepository(database = database)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = PopularDrinkAdapter { drink ->
            val action = HomeFragmentDirections.actionNavigationHomeToDrinkFragment(
                drinkId = drink.id,
                drinkName = drink.name
            )
            findNavController().navigate(action)
        }
        binding.popularDrinkList.adapter = adapter

        viewModel.popularDrinkList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        activity?.addMenuProvider(this)
    }

    override fun onStop() {
        super.onStop()
        activity?.removeMenuProvider(this)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.home_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_item_about -> {
                goToAbout()
                true
            }
            else -> false
        }
    }

    private fun goToAbout() {
        activity?.let {
            val intent = Intent(it, AboutActivity::class.java)
            startActivity(intent)
        }
    }
}