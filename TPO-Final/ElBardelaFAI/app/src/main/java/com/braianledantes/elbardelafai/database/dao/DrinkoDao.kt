package com.braianledantes.elbardelafai.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.braianledantes.elbardelafai.database.entities.DrinkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkDao {
    @Query("select * from drink where name like :query order by name asc")
    fun getDrinksByName(query: String) : PagingSource<Int, DrinkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(drink: DrinkEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(drinks: List<DrinkEntity>)

    @Query("delete from drink")
    suspend fun clearAll()

}