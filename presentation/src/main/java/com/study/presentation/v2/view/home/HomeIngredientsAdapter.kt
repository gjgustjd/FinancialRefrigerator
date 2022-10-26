package com.study.presentation.v2.view.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.study.domain.model.IngredientItem
import com.study.presentation.R
import com.study.presentation.databinding.ItemIngredientHomeBinding
import com.study.presentation.v2.base.BaseAdapter
import com.study.presentation.v2.base.BaseViewHolder
import com.study.presentation.v2.view.crawlRecipe.CrawlRecipesActivity

class HomeIngredientsAdapter(
    context: Context
) : BaseAdapter<IngredientItem, HomeIngredientsAdapter.Holder>(context) {
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
            itemBinding.item.setOnClickListener {
                context?.startActivity(
                    Intent(context, CrawlRecipesActivity::class.java)
                        .putExtra(
                            CrawlRecipesActivity.SEARCH_TYPE,
                            CrawlRecipesActivity.TYPE_INGREDIENT
                        )
                        .putExtra(CrawlRecipesActivity.SEARCH_KEYWORD, item.name)
                )
            }
        }
    }
}