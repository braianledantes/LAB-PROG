package com.braianledantes.elbardelafai.database

import android.content.Context
import androidx.room.*

@Database(entities = [DrinkEntity::class], version = 1)
abstract class ElBarDeLaFAIDatabase : RoomDatabase() {
    abstract val drinkDao: DrinkDao

    companion object {
        private var INSTANCE: ElBarDeLaFAIDatabase? = null

        fun getDatabase(context: Context): ElBarDeLaFAIDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ElBarDeLaFAIDatabase::class.java,
                    "elbardelafai"
                )
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}


