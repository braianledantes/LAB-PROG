package com.braianledantes.elbardelafai.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.braianledantes.elbardelafai.database.ElBarDeLaFAIDatabase
import com.braianledantes.elbardelafai.domain.Ingredient
import com.braianledantes.elbardelafai.network.ElBarDeLaFaiNetwork
import com.braianledantes.elbardelafai.network.ElBarDeLaFaiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val INGREDIENTS_PAGE_SIZE = 20

class IngredientsRepository(
    private val database: ElBarDeLaFAIDatabase,
    private val service: ElBarDeLaFaiService = ElBarDeLaFaiNetwork.service
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPagingIngredients(): Flow<PagingData<Ingredient>> {
        return Pager(
            config = PagingConfig(
                pageSize = INGREDIENTS_PAGE_SIZE,
                initialLoadSize = INGREDIENTS_PAGE_SIZE
            ),
            remoteMediator = IngredientRemoteMediator(
                service = service,
                database = database
            ),
            pagingSourceFactory = { database.ingredientDao.getPagingIngredients() }
        )
            .flow
            .map { pagingData ->
                pagingData.map { it.toDomainModel() }
            }
    }
}