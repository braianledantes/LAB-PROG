package com.braianledantes.elbardelafai.domain

import com.braianledantes.elbardelafai.network.NetworkDrink
import com.braianledantes.elbardelafai.util.smartTruncate

data class Drink(
    val id: String,
    val name: String,
    val instructions: String,
    val imageUrl: String
) {

    val shortInstructions: String
        get() = instructions.smartTruncate(200)

    fun toNetworkModel() = NetworkDrink(
        id = this.id,
        name = this.name,
        instructions = this.instructions,
        imageUrl = this.imageUrl,
        ingredients = emptyList()
    )
}