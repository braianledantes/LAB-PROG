package com.braianledantes.elbardelafai.ui.ingredients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.braianledantes.elbardelafai.repository.IngredientsRepository

class IngredientsViewModel(
    private val ingredientsRepository: IngredientsRepository
) : ViewModel() {

    val pagingIngredients = ingredientsRepository.getPagingIngredients()
        .cachedIn(viewModelScope)
}

class IngredientsViewModelFactory(
    private val ingredientsRepository: IngredientsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientsViewModel::class.java))
            return IngredientsViewModel(ingredientsRepository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}