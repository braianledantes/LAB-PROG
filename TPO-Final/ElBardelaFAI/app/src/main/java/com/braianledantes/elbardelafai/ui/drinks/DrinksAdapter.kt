package com.braianledantes.elbardelafai.ui.drinks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.braianledantes.elbardelafai.databinding.ItemDrinkBinding
import com.braianledantes.elbardelafai.domain.Drink

class DrinksAdapter(private val onDrinkClicked: (Drink) -> Unit) :
    PagingDataAdapter<Drink, DrinkViewHolder>(DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item, onDrinkClicked)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        return DrinkViewHolder(
            ItemDrinkBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Drink>() {
            override fun areItemsTheSame(
                oldItem: Drink,
                newItem: Drink
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Drink,
                newItem: Drink
            ): Boolean = oldItem == newItem
        }
    }
}