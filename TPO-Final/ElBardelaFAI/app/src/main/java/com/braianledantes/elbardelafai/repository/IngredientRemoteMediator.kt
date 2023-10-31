package com.braianledantes.elbardelafai.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.braianledantes.elbardelafai.database.ElBarDeLaFAIDatabase
import com.braianledantes.elbardelafai.database.entities.IngredientEntity
import com.braianledantes.elbardelafai.network.ElBarDeLaFaiService
import com.braianledantes.elbardelafai.network.toDatabaseModel
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE = 1

@OptIn(ExperimentalPagingApi::class)
class IngredientRemoteMediator(
    private val query: String,
    private val service: ElBarDeLaFaiService,
    private val database: ElBarDeLaFAIDatabase
) : RemoteMediator<Int, IngredientEntity>() {

    private val ingredientDao = database.ingredientDao

    private var lastPage = STARTING_PAGE

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, IngredientEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> STARTING_PAGE
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> ++lastPage
            }

            val response = service.getIngredientsList(
                search = query,
                page = page,
                pageSize = state.config.pageSize
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH)
                    ingredientDao.deleteAll()
                ingredientDao.insertAll(response.ingredients.toDatabaseModel())
            }

            MediatorResult.Success(
                endOfPaginationReached = response.ingredients.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}