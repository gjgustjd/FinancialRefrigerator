package com.study.financialrefrigerator.presentation.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.databinding.ItemIngredientHomeBinding
import com.study.financialrefrigerator.model.ingredient.IngredientItem

class HomeIngredientsAdapter constructor(
    private var items: List<IngredientItem>
) : RecyclerView.Adapter<HomeIngredientsAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ingredient_home, parent, false)
        return Holder(ItemIngredientHomeBinding.bind(view))
    }

    fun setItems(updatedItems: List<IngredientItem>) {
        items = updatedItems
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
        Log.i("ingredientsAdapter",items[position].name)
    }

    override fun getItemCount() = items.count()

    class Holder(val itemBinding: ItemIngredientHomeBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var name: String = ""
        fun bind(item: IngredientItem) {
            itemBinding.ingredient = item
        }
    }
}