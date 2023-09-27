package com.braianledantes.elbardelafai.network

import com.braianledantes.elbardelafai.database.DrinkEntity
import com.braianledantes.elbardelafai.domain.Drink
import com.google.gson.annotations.SerializedName

data class NetworkDrinkListContainer(
    @SerializedName("page") val page: Int,
    @SerializedName("maxPages") val maxPages: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("drinks") val drinks: List<NetworkDrink>
)

data class NetworkDrink(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("instructions") val instructions: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("ingredients") val ingredients: List<String>
) {

    fun toDatabaseModel() = DrinkEntity(
        id = this.id,
        name = this.name,
        instructions = this.instructions,
        imageUrl = this.imageUrl
    )

    fun toDomainModel() = Drink(
        id = this.id,
        name = this.name,
        instructions = this.instructions,
        imageUrl = this.imageUrl
    )
}

fun List<NetworkDrink>.toDomainModel() = map { it.toDomainModel() }
fun List<NetworkDrink>.toDatabaseModel() = map { it.toDatabaseModel() }

fun NetworkDrinkListContainer.toDomainModel(): List<Drink> {
    return drinks.map { it.toDomainModel() }
}

fun NetworkDrinkListContainer.toDatabaseModel(): List<DrinkEntity> {
    return drinks.map { it.toDatabaseModel() }
}
