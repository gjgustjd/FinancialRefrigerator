package com.study.presentation.old.presentation.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.study.presentation.R
import com.study.presentation.old.presentation.base.BaseAdapter
import com.study.presentation.old.presentation.base.BaseViewHolder
import com.study.presentation.databinding.ItemIngredientHomeBinding
import com.study.presentation.old.model.ingredient.IngredientItem
import com.study.presentation.old.presentation.activity.search.SearchRecipesActivity

class HomeIngredientsAdapter (
    context: Context,
    items: List<IngredientItem> = listOf(),
    diffUtil: DiffUtil.ItemCallback<IngredientItem> = getDefaultDiffUtil()
) : BaseAdapter<IngredientItem, HomeIngredientsAdapter.Holder>(context,items,diffUtil)
{
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
                context?.startActivity(
                    Intent(context, SearchRecipesActivity::class.java)
                        .putExtra(SearchRecipesActivity.SEARCH_TYPE, SearchRecipesActivity.TYPE_INGREDIENT)
                        .putExtra(SearchRecipesActivity.SEARCH_KEYWORD,item.name)
                )
            }
        }
    }
}