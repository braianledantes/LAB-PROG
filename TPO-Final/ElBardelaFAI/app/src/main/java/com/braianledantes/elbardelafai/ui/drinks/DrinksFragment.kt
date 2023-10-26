package com.braianledantes.elbardelafai.ui.drinks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.braianledantes.elbardelafai.App
import com.braianledantes.elbardelafai.R
import com.braianledantes.elbardelafai.databinding.FragmentDrinksBinding
import com.braianledantes.elbardelafai.repository.DrinksRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DrinksFragment : Fragment() {

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
        val adapter = DrinksAdapter { drink ->
            val action = DrinksFragmentDirections.actionNavigationDrinksToDrinkFragment(
                drinkId = drink.id,
                drinkName = drink.name
            )
            findNavController().navigate(action)
        }
        binding.drinkList.adapter = adapter

        lifecycleScope.launch {
            viewModel.pagingDrinks.collectLatest { pagingData ->
                adapter.submitData(pagingData)
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
}