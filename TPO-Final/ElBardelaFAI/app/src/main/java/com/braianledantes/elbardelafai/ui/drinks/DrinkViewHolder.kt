package com.braianledantes.elbardelafai.ui.drinks

import androidx.recyclerview.widget.RecyclerView
import com.braianledantes.elbardelafai.databinding.ItemDrinkBinding
import com.braianledantes.elbardelafai.domain.Drink

class DrinkViewHolder(
    private val binding: ItemDrinkBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(drink: Drink, onDrinkClicked: (Drink) -> Unit) {
        binding.apply {
            itemView.setOnClickListener { onDrinkClicked(drink) }
            this.drink = drink
        }
    }
}