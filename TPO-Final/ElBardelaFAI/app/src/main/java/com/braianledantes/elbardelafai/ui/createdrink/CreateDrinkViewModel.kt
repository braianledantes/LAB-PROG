package com.braianledantes.elbardelafai.ui.createdrink

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.braianledantes.elbardelafai.core.Resource
import com.braianledantes.elbardelafai.domain.Drink
import com.braianledantes.elbardelafai.repository.DrinksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.URL

class CreateDrinkViewModel(
    private val drinksRepository: DrinksRepository
) : ViewModel() {

    val state = MutableStateFlow<Resource<Drink>>(Resource.Undefined)

    fun createDrink(name: String, instructions: String, imageUrl: String) {
        viewModelScope.launch {
            try {
                if (
                    isNameValid(name) &&
                    areInstructionsValid(instructions) &&
                    isImageUrlEntryValid(imageUrl)
                ) {
                    state.emit(Resource.Loading)

                    val newDrink = Drink(
                        id = "",
                        name = name,
                        instructions = instructions,
                        imageUrl = imageUrl
                    )
                    val drink = drinksRepository.createDrink(newDrink)

                    state.emit(Resource.Success(drink))
                }
            } catch (e: HttpException) {
                state.emit(Resource.Failure(e))
            } catch (e: IOException) {
                state.emit(Resource.Failure(e))
            }
        }
    }

    fun isNameValid(name: String) = name.isNotBlank()

    fun areInstructionsValid(name: String) = name.isNotBlank()

    fun isImageUrlEntryValid(url: String): Boolean {
        return try {
            URL(url)
            true
        } catch (e: Exception) {
            false
        }
    }

}

class CreateDrinkViewModelFactory(
    private val drinksRepository: DrinksRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateDrinkViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateDrinkViewModel(drinksRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}