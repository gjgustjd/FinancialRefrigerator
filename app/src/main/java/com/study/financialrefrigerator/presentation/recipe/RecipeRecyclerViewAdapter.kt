package com.study.financialrefrigerator.presentation.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.study.financialrefrigerator.base.BaseAdapter
import com.study.financialrefrigerator.base.BaseViewHolder
import com.study.financialrefrigerator.databinding.ItemRecipeSearchBinding
import com.study.financialrefrigerator.model.meal.MealAndRecipeItem

class RecipeRecyclerViewAdapter(itemList:List<MealAndRecipeItem> = listOf()) :
    BaseAdapter<MealAndRecipeItem, BaseViewHolder<MealAndRecipeItem>>(itemList = itemList) {
    var isDeleting = false
        set(value) {
            field = value
        }

    inner class RecipeRecyclerViewHolder(private val binding: ItemRecipeSearchBinding) :
        BaseViewHolder<MealAndRecipeItem>(binding) {
        override fun bind(item: MealAndRecipeItem) = with(binding) {
            recipe = item.recipe
            btnDelete.visibility =
                if (isDeleting)
                    View.VISIBLE
                else
                    View.GONE
        }
    }

    fun refreshData()
    {
        val tempList = itemList.toMutableList()
        setItems(listOf())
        setItems(tempList.toList())
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MealAndRecipeItem> {
        val view = ItemRecipeSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeRecyclerViewHolder(view)
    }
}