package com.braianledantes.elbardelafai.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.braianledantes.elbardelafai.core.Resource
import com.braianledantes.elbardelafai.database.ElBarDeLaFAIDatabase
import com.braianledantes.elbardelafai.domain.Drink
import com.braianledantes.elbardelafai.domain.DrinkWithIngredients
import com.braianledantes.elbardelafai.network.ElBarDeLaFaiNetwork
import com.braianledantes.elbardelafai.network.ElBarDeLaFaiService
import com.braianledantes.elbardelafai.network.NetworkIngredient
import com.braianledantes.elbardelafai.network.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

private const val DRINKS_PAGE_SIZE = 20

class DrinksRepository(
    private val service: ElBarDeLaFaiService = ElBarDeLaFaiNetwork.service,
    private val database: ElBarDeLaFAIDatabase
) {

    fun getDrinkFromApi(id: String) = flow {

        emit(Resource.Loading)
        try {
            val drink = service.getDrink(id)
            val ingredients = drink.ingredients.map { ingredientName ->
                val name = ingredientName.replace(" ", "%20")
                try {
                    service.getIngredientByName(name)
                } catch (e: Exception) {
                    // devuelve un ingrediente sin imagen
                    NetworkIngredient(
                        name = ingredientName,
                        srcImage = ""
                    )
                }
            }
            emit(
                Resource.Success(
                    DrinkWithIngredients(
                        drink = drink.toDomainModel(),
                        ingredients = ingredients.toDomainModel()
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }

    }

    suspend fun createDrink(drink: Drink): Drink = withContext(Dispatchers.IO) {
        val drinkModel = drink.toNetworkModel()
        val newDrink = service.createDrink(drinkModel)
        database.drinkDao.insert(newDrink.toDatabaseModel())
        newDrink.toDomainModel()
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getPagingDrinks(query: String): Flow<PagingData<Drink>> {
        return Pager(
            config = PagingConfig(
                pageSize = DRINKS_PAGE_SIZE,
                initialLoadSize = DRINKS_PAGE_SIZE
            ),
            remoteMediator = DrinkRemoteMediator(query, service, database),
            pagingSourceFactory = {
                val dbQuery = "%${query.replace(' ', '%')}%"
                database.drinkDao.getDrinksByName(dbQuery)
            }
        )
            .flow
            .map { pagingData ->
                pagingData.map { it.toDomainModel() }
            }
    }

}
