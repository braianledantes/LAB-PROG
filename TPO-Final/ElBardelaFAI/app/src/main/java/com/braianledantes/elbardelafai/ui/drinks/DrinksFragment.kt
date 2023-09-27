package com.braianledantes.elbardelafai.ui.drinks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.braianledantes.elbardelafai.App
import com.braianledantes.elbardelafai.databinding.FragmentDrinksBinding
import com.braianledantes.elbardelafai.repository.DrinksRepository
import com.braianledantes.elbardelafai.vm.DrinksViewModel
import com.braianledantes.elbardelafai.vm.DrinksViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DrinksFragment : Fragment() {

    private var _binding: FragmentDrinksBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DrinksViewModel by activityViewModels {
        val application = (activity?.application as App)
        val database = application.database
        DrinksViewModelFactory(
            DrinksRepository(database)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDrinksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = DrinksAdapter()
        binding.drinkList.adapter = adapter

        lifecycleScope.launch {
            viewModel.drinks.collectLatest { pagindData ->
                adapter.submitData(pagindData)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}