package com.braianledantes.elbardelafai.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.braianledantes.elbardelafai.database.entities.DrinkEntity
import com.braianledantes.elbardelafai.database.ElBarDeLaFAIDatabase
import com.braianledantes.elbardelafai.network.ElBarDeLaFaiService
import com.braianledantes.elbardelafai.network.toDatabaseModel
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE = 1

@OptIn(ExperimentalPagingApi::class)
class DrinkRemoteMediator(
    private val service: ElBarDeLaFaiService,
    private val database: ElBarDeLaFAIDatabase
) : RemoteMediator<Int, DrinkEntity>() {

    private val drinkDao = database.drinkDao

    private var lastPage = STARTING_PAGE

    override suspend fun load(loadType: LoadType, state: PagingState<Int, DrinkEntity>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> STARTING_PAGE
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> ++lastPage
            }

            val response = service.getDrinkList(page = page, pageSize = state.config.pageSize)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    drinkDao.clearAll()
                }
                drinkDao.insertAll(response.drinks.toDatabaseModel())
            }

            MediatorResult.Success(
                endOfPaginationReached = response.drinks.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
