package com.braianledantes.elbardelafai.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.braianledantes.elbardelafai.database.entities.IngredientEntity

@Dao
interface IngredientDao {
    @Query("select * from ingredient where name like :query order by name")
    fun getPagingIngredients(query: String) : PagingSource<Int, IngredientEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ingredients: List<IngredientEntity>)

    @Query("delete from ingredient")
    suspend fun deleteAll()
}