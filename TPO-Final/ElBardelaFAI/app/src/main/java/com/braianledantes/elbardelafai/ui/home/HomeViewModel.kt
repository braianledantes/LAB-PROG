package com.braianledantes.elbardelafai.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.braianledantes.elbardelafai.repository.DrinksRepository
import com.braianledantes.elbardelafai.repository.PopularDrinksRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val drinksRepository: PopularDrinksRepository
) : ViewModel() {

    val popularDrinkList = drinksRepository.popularDrinkList.asLiveData()

    init {
        viewModelScope.launch {
            drinksRepository.refreshPopularDrinks()
        }
    }

}

class HomeViewModelFactory(
    private val popularDrinksRepository: PopularDrinksRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(popularDrinksRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}