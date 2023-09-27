package com.braianledantes.elbardelafai.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.braianledantes.elbardelafai.database.DrinkDao
import com.braianledantes.elbardelafai.database.ElBarDeLaFAIDatabase
import com.braianledantes.elbardelafai.database.asDomainModel
import com.braianledantes.elbardelafai.domain.Drink
import com.braianledantes.elbardelafai.network.ElBarDeLaFaiService
import com.braianledantes.elbardelafai.network.toDatabaseModel
import com.braianledantes.elbardelafai.network.toDomainModel
import kotlin.math.max

class DrinkPagingSource(
    val backend: ElBarDeLaFaiService,
    val drinksDao: DrinkDao,
    val query: String
): PagingSource<Int, Drink>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Drink> {
        try {
            val nextPageNumber = params.key ?: 1

            val response = backend.getDrinkList(
                search = query,
                page = nextPageNumber,
                pageSize = 24
            )

            return LoadResult.Page(
                data = response.drinks.toDomainModel(),
                prevKey = null,
                nextKey = response.page + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Drink>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}