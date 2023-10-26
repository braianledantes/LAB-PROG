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
    @Query("select * from drink")
    fun getDrinks(): Flow<List<DrinkEntity>>

    @Query("select * from drink")
    fun getAllDrinks() : PagingSource<Int, DrinkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(drink: DrinkEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(drinks: List<DrinkEntity>)

    @Query("delete from drink")
    suspend fun clearAll()
}