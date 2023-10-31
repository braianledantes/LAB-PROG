package com.braianledantes.elbardelafai.ui.drinks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.braianledantes.elbardelafai.domain.Drink
import com.braianledantes.elbardelafai.repository.DrinksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class DrinksViewModel(
    private val drinksRepository: DrinksRepository
) : ViewModel() {

    val search = MutableStateFlow("")

    val pagingDrinks: Flow<PagingData<Drink>> = search
        .flatMapLatest { drinksRepository.getPagingDrinks(it) }
        .cachedIn(viewModelScope)

    fun searchDrinksByName(query: String) {
        viewModelScope.launch {
            search.emit(query.trim())
        }
    }
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