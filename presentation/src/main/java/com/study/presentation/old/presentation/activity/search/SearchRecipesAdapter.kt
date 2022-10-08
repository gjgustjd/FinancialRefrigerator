package com.study.presentation.old.presentation.activity.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.study.presentation.R
import com.study.presentation.old.presentation.base.BaseAdapter
import com.study.presentation.old.presentation.base.BaseViewHolder
import com.study.presentation.databinding.ItemRecipeSearchBinding
import com.study.presentation.old.model.recipe.RecipeItem
import com.study.presentation.old.presentation.recipe.detail.RecipeDetailActivity

class SearchRecipesAdapter constructor(
    context: Context,
    items: List<RecipeItem>,
) : BaseAdapter<RecipeItem, SearchRecipesAdapter.Holder>(context,items) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recipe_search, parent, false)
        return Holder(ItemRecipeSearchBinding.bind(view))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemList[position])
    }

    inner class Holder(private val itemBinding: ItemRecipeSearchBinding) :
        BaseViewHolder<RecipeItem>(itemBinding) {
        var name: String = ""
        override fun bind(item: RecipeItem) {
            itemBinding.recipe = item
            itemBinding.item.setOnClickListener {
                context?.startActivity(
                    Intent(context, RecipeDetailActivity::class.java)
                        .putExtra(RecipeDetailActivity.KEY_CALL_FROM, RecipeDetailActivity.FROM_HOME)
                        .putExtra(RecipeDetailActivity.KEY_RECIPE_ID,item.id))

            }
        }
    }
}