package com.braianledantes.elbardelafai.repository

import com.braianledantes.elbardelafai.database.ElBarDeLaFAIDatabase
import com.braianledantes.elbardelafai.database.entities.toDomainModel
import com.braianledantes.elbardelafai.network.ElBarDeLaFaiNetwork
import com.braianledantes.elbardelafai.network.ElBarDeLaFaiService
import com.braianledantes.elbardelafai.network.toPopularDrinkEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PopularDrinksRepository(
    private val service: ElBarDeLaFaiService = ElBarDeLaFaiNetwork.service,
    private val database: ElBarDeLaFAIDatabase
) {

    val popularDrinkList = database.popularDrinkDao.getAll().map { it.toDomainModel() }

    suspend fun refreshPopularDrinks() {
        withContext(Dispatchers.IO) {
            val list = service.getCurrentDrinkList()
            database.popularDrinkDao.clearAll()
            database.popularDrinkDao.insertAll(list.toPopularDrinkEntity())
        }
    }
}