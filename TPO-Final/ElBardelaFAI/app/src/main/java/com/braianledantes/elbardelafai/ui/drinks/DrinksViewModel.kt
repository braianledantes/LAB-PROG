package com.braianledantes.elbardelafai.ui.drinks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.braianledantes.elbardelafai.repository.DrinksRepository

class DrinksViewModel(
    private val drinksRepository: DrinksRepository
) : ViewModel() {

    val pagingDrinks = drinksRepository.getPagingDrinks()
        .cachedIn(viewModelScope)
}

class DrinksViewModelFactory(
    private val drinksRepository: DrinksRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrinksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DrinksViewModel(drinksRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}