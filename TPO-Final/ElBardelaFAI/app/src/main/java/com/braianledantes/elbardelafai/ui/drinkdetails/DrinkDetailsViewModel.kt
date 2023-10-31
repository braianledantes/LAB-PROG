package com.braianledantes.elbardelafai.ui.drinkdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.braianledantes.elbardelafai.repository.DrinksRepository

class DrinkDetailsViewModel(
    private val drinksRepository: DrinksRepository
) : ViewModel() {

    fun loadDrinkById(id: String) = drinksRepository.getDrinkFromApi(id)

}

class DrinkDetailsViewModelFactory(
    private val drinksRepository: DrinksRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrinkDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DrinkDetailsViewModel(drinksRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}