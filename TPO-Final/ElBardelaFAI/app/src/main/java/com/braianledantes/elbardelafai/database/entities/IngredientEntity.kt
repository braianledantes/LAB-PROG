package com.braianledantes.elbardelafai.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.braianledantes.elbardelafai.domain.Ingredient

@Entity(tableName = "ingredient")
data class IngredientEntity(
    @PrimaryKey
    val name: String,
    val urlImage: String
) {
    fun toDomainModel() = Ingredient(
        name = this.name,
        urlImage = this.urlImage
    )
}

fun List<IngredientEntity>.toDomainModel() = map { it.toDomainModel() }
