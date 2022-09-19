package com.study.financialrefrigerator.presentation.activity.main.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.base.BaseAdapter
import com.study.financialrefrigerator.base.BaseViewHolder
import com.study.financialrefrigerator.databinding.ItemIngredientHomeBinding
import com.study.financialrefrigerator.model.ingredient.IngredientItem
import com.study.financialrefrigerator.presentation.activity.search.SearchRecipesActivity

class HomeIngredientsAdapter constructor(
    context: Context,
    items: List<IngredientItem> = listOf()
) :BaseAdapter<IngredientItem, HomeIngredientsAdapter.Holder> (context,items){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ingredient_home, parent, false)
        return Holder(ItemIngredientHomeBinding.bind(view))
    }

    inner class Holder(private val itemBinding: ItemIngredientHomeBinding) :
        BaseViewHolder<IngredientItem>(itemBinding) {
        var name: String = ""
        override fun bind(item: IngredientItem) {
            itemBinding.ingredient = item
            itemBinding.item.setOnClickListener{
                context.startActivity(
                    Intent(context,SearchRecipesActivity::class.java)
                        .putExtra("type","ingredient")
                        .putExtra("keyword",item.name)
                )
            }
        }
    }
}