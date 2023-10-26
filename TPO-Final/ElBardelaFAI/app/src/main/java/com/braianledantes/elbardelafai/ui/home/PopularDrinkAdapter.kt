package com.braianledantes.elbardelafai.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.braianledantes.elbardelafai.databinding.ItemPopularDrinkBinding
import com.braianledantes.elbardelafai.domain.Drink

class PopularDrinkAdapter(private val onDrinkClicked: (Drink) -> Unit) :
    ListAdapter<Drink, PopularDrinkAdapter.DrinkViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        return DrinkViewHolder(
            ItemPopularDrinkBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onDrinkClicked)
    }

    inner class DrinkViewHolder(
        private val binding: ItemPopularDrinkBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(drink: Drink, onDrinkClicked: (Drink) -> Unit) {
            binding.apply {
                itemView.setOnClickListener { onDrinkClicked(drink) }
                this.drink = drink
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Drink>() {
            override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean =
                oldItem == newItem
        }
    }
}