package com.braianledantes.elbardelafai.database

import android.content.Context
import androidx.room.*
import com.braianledantes.elbardelafai.database.dao.DrinkDao
import com.braianledantes.elbardelafai.database.dao.IngredientDao
import com.braianledantes.elbardelafai.database.dao.PopularDrinkDao
import com.braianledantes.elbardelafai.database.entities.DrinkEntity
import com.braianledantes.elbardelafai.database.entities.IngredientEntity
import com.braianledantes.elbardelafai.database.entities.PopularDrinkEntity

@Database(
    entities = [
        DrinkEntity::class,
        PopularDrinkEntity::class,
        IngredientEntity::class
    ],
    version = 2
)
abstract class ElBarDeLaFAIDatabase : RoomDatabase() {
    abstract val drinkDao: DrinkDao
    abstract val popularDrinkDao: PopularDrinkDao
    abstract val ingredientDao: IngredientDao

    companion object {
        private var INSTANCE: ElBarDeLaFAIDatabase? = null

        fun getDatabase(context: Context): ElBarDeLaFAIDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ElBarDeLaFAIDatabase::class.java,
                    "elbardelafai.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}


