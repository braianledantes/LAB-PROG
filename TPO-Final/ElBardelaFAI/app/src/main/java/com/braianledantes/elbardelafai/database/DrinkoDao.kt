package com.braianledantes.elbardelafai.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkDao {
    @Query("select * from drink")
    fun getDrinks(): Flow<List<DrinkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(drinks: List<DrinkEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(drink: DrinkEntity)

    @Query("delete from drink")
    fun deleteAll()
}