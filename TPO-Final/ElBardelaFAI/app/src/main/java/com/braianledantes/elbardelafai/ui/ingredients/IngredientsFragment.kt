package com.braianledantes.elbardelafai.ui.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.braianledantes.elbardelafai.App
import com.braianledantes.elbardelafai.databinding.FragmentIngredientsBinding
import com.braianledantes.elbardelafai.repository.IngredientsRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class IngredientsFragment : Fragment() {

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
        val adapter = IngredientsAdapter()
        binding.ingredientList.adapter = adapter

        lifecycleScope.launch {
            viewModel.pagingIngredients.collectLatest { pagingIngredients ->
                adapter.submitData(pagingIngredients)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}