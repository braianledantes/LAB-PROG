package com.braianledantes.elbardelafai.ui.createdrink

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.braianledantes.elbardelafai.App
import com.braianledantes.elbardelafai.R
import com.braianledantes.elbardelafai.core.Resource
import com.braianledantes.elbardelafai.databinding.FragmentCreateDrinkBinding
import com.braianledantes.elbardelafai.repository.DrinksRepository
import kotlinx.coroutines.launch

class CreateDrinkFragment : Fragment() {

    private var _binding: FragmentCreateDrinkBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateDrinkViewModel by viewModels {
        val application = activity?.application as App
        val database = application.database
        CreateDrinkViewModelFactory(
            DrinksRepository(database = database)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateDrinkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            imageUrlTextField.editText?.setOnEditorActionListener { v, actionId, event ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        createDrink()
                        true
                    }

                    else -> false
                }
            }
            btnAccept.setOnClickListener {
                createDrink()
            }
            btnCancel.setOnClickListener {
                findNavController().navigateUp()
            }
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is Resource.Undefined -> {
                        setLoading(false)
                        // do nothing :)
                    }

                    is Resource.Loading -> {
                        setLoading(true)
                    }

                    is Resource.Success -> {
                        setLoading(false)

                        AlertDialog.Builder(requireContext())
                            .setMessage(getString(R.string.message_dialog_creating_drink))
                            .setPositiveButton(getString(R.string.accept_option)) { d, _ ->
                                d.cancel()
                                findNavController().navigateUp()
                            }
                            .show()
                    }

                    is Resource.Failure -> {
                        setLoading(false)

                        AlertDialog.Builder(requireContext())
                            .setMessage(state.exception.message)
                            .setPositiveButton(getString(R.string.accept_option), null)
                            .show()
                    }
                }
            }
        }
    }

    private fun createDrink() {
        val name = binding.nameTextField.editText?.text.toString()
        val instructions = binding.instructionsTextField.editText?.text.toString()
        val imageUrl = binding.imageUrlTextField.editText?.text.toString()

        val isNameValid = viewModel.isNameValid(name)
        val areInstructionsValid = viewModel.areInstructionsValid(instructions)
        val isImageUrlValid = viewModel.isImageUrlEntryValid(imageUrl)

        val invalidText = getString(R.string.error_invalid)
        binding.nameTextField.error = if (isNameValid) null else invalidText
        binding.instructionsTextField.error = if (areInstructionsValid) null else invalidText
        binding.imageUrlTextField.error = if (isImageUrlValid) null else invalidText

        viewModel.createDrink(name, instructions, imageUrl)
    }

    private fun setLoading(loading: Boolean) {
        if (loading) {
            binding.btnAccept.isEnabled = false
            binding.progressBar.isVisible = true
            binding.nameTextField.isEnabled = false
            binding.instructionsTextField.isEnabled = false
            binding.imageUrlTextField.isEnabled = false
        } else {
            binding.btnAccept.isEnabled = true
            binding.progressBar.isVisible = false
            binding.nameTextField.isEnabled = true
            binding.instructionsTextField.isEnabled = true
            binding.imageUrlTextField.isEnabled = true
        }
    }
}