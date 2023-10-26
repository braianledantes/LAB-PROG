package com.braianledantes.elbardelafai.ui.drinkdetails

import androidx.recyclerview.widget.RecyclerView
import com.braianledantes.elbardelafai.databinding.ItemIngredientBinding
import com.braianledantes.elbardelafai.domain.Ingredient

class DrinkIngredientViewHolder(
    private val binding: ItemIngredientBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(ingredient: Ingredient) {
        binding.apply {
            this.ingredient = ingredient
        }
    }
}