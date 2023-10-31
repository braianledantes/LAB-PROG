package com.braianledantes.elbardelafai.ui.ingredients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.braianledantes.elbardelafai.domain.Ingredient
import com.braianledantes.elbardelafai.repository.IngredientsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class IngredientsViewModel(
    private val ingredientsRepository: IngredientsRepository
) : ViewModel() {

    val search = MutableStateFlow("")

    val pagingIngredients: Flow<PagingData<Ingredient>> = search
        .flatMapLatest { ingredientsRepository.getPagingIngredients(it) }
        .cachedIn(viewModelScope)

    fun searchIngredientsByName(query: String) {
        viewModelScope.launch {
            search.emit(query.trim())
        }
    }
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