package com.braianledantes.elbardelafai.network

import com.braianledantes.elbardelafai.database.entities.IngredientEntity
import com.braianledantes.elbardelafai.domain.Ingredient
import com.google.gson.annotations.SerializedName

data class NetworkIngredientListContainer(
    @SerializedName("page") val page: Int,
    @SerializedName("maxPages") val maxPages: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("ingredients") val ingredients: List<NetworkIngredient>
)

data class NetworkIngredient(
    @SerializedName("name") val name: String,
    @SerializedName("srcImage") val srcImage: String
) {
    fun toDomainModel() = Ingredient(
        name = this.name,
        urlImage = this.srcImage
    )

    fun toDatabaseModel() = IngredientEntity(
        name = this.name,
        urlImage = this.srcImage
    )
}

fun List<NetworkIngredient>.toDomainModel() = map { it.toDomainModel() }

fun List<NetworkIngredient>.toDatabaseModel() = map { it.toDatabaseModel() }