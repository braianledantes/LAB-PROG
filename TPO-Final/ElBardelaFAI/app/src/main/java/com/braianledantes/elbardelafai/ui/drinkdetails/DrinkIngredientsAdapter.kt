package com.braianledantes.elbardelafai.ui.drinkdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.braianledantes.elbardelafai.databinding.ItemIngredientBinding
import com.braianledantes.elbardelafai.domain.Ingredient

class DrinkIngredientsAdapter : ListAdapter<Ingredient, DrinkIngredientViewHolder>(DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: DrinkIngredientViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkIngredientViewHolder {
        return DrinkIngredientViewHolder(
            ItemIngredientBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Ingredient>() {
            override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
                return oldItem == newItem
            }
        }
    }
}