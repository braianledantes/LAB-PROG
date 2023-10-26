package com.braianledantes.elbardelafai.database.entities

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
) {
    fun toDomainModel() = Drink(
        id = this.id,
        name = this.name,
        instructions = this.instructions,
        imageUrl = this.imageUrl
    )
}

fun List<DrinkEntity>.toDomainModel(): List<Drink> {
    return map { it.toDomainModel() }
}
