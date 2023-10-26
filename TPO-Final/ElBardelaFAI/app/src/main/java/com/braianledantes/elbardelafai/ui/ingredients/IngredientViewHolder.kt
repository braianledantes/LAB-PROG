package com.braianledantes.elbardelafai.ui.ingredients

import androidx.recyclerview.widget.RecyclerView
import com.braianledantes.elbardelafai.databinding.ItemIngredientBinding
import com.braianledantes.elbardelafai.domain.Ingredient

class IngredientViewHolder(
    private val binding: ItemIngredientBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(ingredient: Ingredient) {
        binding.apply {
            this.ingredient = ingredient
        }
    }
}