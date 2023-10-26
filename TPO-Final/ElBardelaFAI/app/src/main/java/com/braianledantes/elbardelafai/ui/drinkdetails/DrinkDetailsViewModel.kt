package com.braianledantes.elbardelafai.ui.drinkdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.braianledantes.elbardelafai.core.Resource
import com.braianledantes.elbardelafai.domain.DrinkWithIngredients
import com.braianledantes.elbardelafai.repository.DrinksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch

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