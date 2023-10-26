package com.braianledantes.elbardelafai.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.braianledantes.elbardelafai.database.entities.PopularDrinkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PopularDrinkDao {
    @Query("select * from popular_drink")
    fun getAll() : Flow<List<PopularDrinkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<PopularDrinkEntity>)


    @Query("delete from popular_drink")
    suspend fun clearAll()
}