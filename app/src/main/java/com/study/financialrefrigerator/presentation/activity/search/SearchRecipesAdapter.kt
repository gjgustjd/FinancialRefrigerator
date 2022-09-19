package com.study.financialrefrigerator.presentation.activity.search

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.databinding.ItemRecipeSearchBinding
import com.study.financialrefrigerator.model.recipe.RecipeItem
import com.study.financialrefrigerator.presentation.recipe.detail.RecipeDetailActivity

class SearchRecipesAdapter constructor(
    private val context: Context,
    private var items: List<RecipeItem>
) : RecyclerView.Adapter<SearchRecipesAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recipe_search, parent, false)
        return Holder(ItemRecipeSearchBinding.bind(view))
    }

    fun setItems(updatedItems: List<RecipeItem>) {
        items = updatedItems
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
        Log.i("ingredientsAdapter",items[position].name)
    }

    override fun getItemCount() = items.count()

    inner class Holder(val itemBinding: ItemRecipeSearchBinding) :
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