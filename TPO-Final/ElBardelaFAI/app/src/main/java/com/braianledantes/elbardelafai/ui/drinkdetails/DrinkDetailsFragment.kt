package com.braianledantes.elbardelafai.ui.drinkdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.braianledantes.elbardelafai.App
import com.braianledantes.elbardelafai.core.Resource
import com.braianledantes.elbardelafai.databinding.FragmentDrinkBinding
import com.braianledantes.elbardelafai.domain.Drink
import com.braianledantes.elbardelafai.repository.DrinksRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class DrinkDetailsFragment : Fragment() {

    private val args: DrinkDetailsFragmentArgs by navArgs()

    private var _binding: FragmentDrinkBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DrinkDetailsViewModel by activityViewModels {
        val application = activity?.application as App
        val database = application.database
        DrinkDetailsViewModelFactory(
            DrinksRepository(database = database)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrinkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = DrinkIngredientsAdapter()
        binding.ingredientsList.adapter = adapter

        // Para que cargue algo
        binding.drink = Drink("", "", "", "")

        lifecycleScope.launch {
            viewModel.loadDrinkById(args.drinkId).collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.image.isVisible = false
                    }

                    is Resource.Failure -> {
                        binding.image.isVisible = true
                        Snackbar.make(
                            view, it.exception.message ?: "Error", Snackbar.LENGTH_LONG
                        ).show()
                    }

                    is Resource.Success -> {
                        binding.image.isVisible = true
                        binding.drink = it.data.drink
                        adapter.submitList(it.data.ingredients)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}