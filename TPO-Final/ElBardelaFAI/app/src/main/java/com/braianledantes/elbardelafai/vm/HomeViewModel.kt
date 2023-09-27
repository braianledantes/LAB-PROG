package com.braianledantes.elbardelafai.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.braianledantes.elbardelafai.repository.DrinksRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val drinksRepository: DrinksRepository
) : ViewModel() {

    val popularDrinkList = drinksRepository.popularDrinkList.asLiveData()

    init {
        viewModelScope.launch {
            drinksRepository.refreshPopularDrinks()
        }
    }

}

class HomeViewModelFactory(
    private val drinksRepository: DrinksRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(drinksRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}