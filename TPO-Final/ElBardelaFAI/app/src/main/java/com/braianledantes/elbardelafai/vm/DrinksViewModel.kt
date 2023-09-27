package com.braianledantes.elbardelafai.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.braianledantes.elbardelafai.repository.DrinkPagingSource
import com.braianledantes.elbardelafai.repository.DrinksRepository

class DrinksViewModel(
    private val drinksRepository: DrinksRepository
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is drinks Fragment"
    }
    val text: LiveData<String> = _text

    val drinks = Pager(
        PagingConfig(pageSize = 24)
    ) {
        drinksRepository.drinkPagingSource("")
    }
        .flow
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