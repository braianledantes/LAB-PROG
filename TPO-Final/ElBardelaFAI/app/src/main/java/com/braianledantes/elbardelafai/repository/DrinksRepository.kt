package com.braianledantes.elbardelafai.repository

import com.braianledantes.elbardelafai.database.ElBarDeLaFAIDatabase
import com.braianledantes.elbardelafai.database.asDomainModel
import com.braianledantes.elbardelafai.domain.Drink
import com.braianledantes.elbardelafai.network.ElBarDeLaFaiNetwork
import com.braianledantes.elbardelafai.network.toDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DrinksRepository(private val database: ElBarDeLaFAIDatabase) {

    val popularDrinkList = database.drinkDao.getDrinks().map { it.asDomainModel() }

    suspend fun refreshPopularDrinks() {
        withContext(Dispatchers.IO) {
            val list = ElBarDeLaFaiNetwork.service.getCurrentDrinkList()
            database.drinkDao.deleteAll()
            database.drinkDao.insert(list.toDatabaseModel())
        }
    }

    suspend fun createDrink(drink: Drink): Drink = withContext(Dispatchers.IO) {
        val drinkModel = drink.toNetworkModel()
        val newDrink = ElBarDeLaFaiNetwork.service.createDrink(drinkModel)
        database.drinkDao.insert(newDrink.toDatabaseModel())
        newDrink.toDomainModel()
    }

    fun drinkPagingSource(search: String) = DrinkPagingSource(
        backend = ElBarDeLaFaiNetwork.service,
        drinksDao = database.drinkDao,
        query = search
    )

}
