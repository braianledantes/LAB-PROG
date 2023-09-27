package com.braianledantes.elbardelafai.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.braianledantes.elbardelafai.domain.Drink

@Entity(tableName = "drink")
data class DrinkEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val instructions: String,
    val imageUrl: String
)

fun List<DrinkEntity>.asDomainModel(): List<Drink> {
    return map {
        Drink(
            id = it.id,
            name = it.name,
            instructions = it.instructions,
            imageUrl = it.imageUrl
        )
    }
}
