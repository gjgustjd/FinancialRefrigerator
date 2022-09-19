package com.study.financialrefrigerator.presentation.activity.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.base.BaseAdapter
import com.study.financialrefrigerator.databinding.ItemRecipeSearchBinding
import com.study.financialrefrigerator.model.recipe.RecipeItem
import com.study.financialrefrigerator.presentation.activity.recipeDetail.RecipeDetailActivity

class SearchRecipesAdapter constructor(
    context: Context,
    items: List<RecipeItem>
) :BaseAdapter<RecipeItem,SearchRecipesAdapter.Holder>(context,items) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recipe_search, parent, false)
        return Holder(ItemRecipeSearchBinding.bind(view))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount() = itemList.count()

    inner class Holder(private val itemBinding: ItemRecipeSearchBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var name: String = ""
        fun bind(item: RecipeItem) {
            itemBinding.recipe = item
            itemBinding.item.setOnClickListener{
                context.startActivity(
                    Intent(context,RecipeDetailActivity::class.java)
                        .putExtra("recipe_id",item.id))

            }
        }
    }
}