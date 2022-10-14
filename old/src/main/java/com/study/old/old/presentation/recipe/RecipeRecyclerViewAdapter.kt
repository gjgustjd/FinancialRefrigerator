package com.study.old.old.presentation.recipe

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.study.old.R
import com.study.old.old.presentation.base.BaseAdapter
import com.study.old.old.presentation.base.BaseViewHolder
import com.study.old.old.common.DialogUtil
import com.study.old.databinding.ItemRecipeSearchBinding
import com.study.old.old.model.meal.MealAndRecipeItem
import com.study.old.old.presentation.recipe.detail.RecipeDetailActivity

class RecipeRecyclerViewAdapter(
    context: Context,
    itemList: List<MealAndRecipeItem> = listOf(),
    val viewModel: RecipeViewModel
) :
    BaseAdapter<MealAndRecipeItem, BaseViewHolder<MealAndRecipeItem>>(
        context = context,
        itemList = itemList
    ) {
    var isDeleting = false

    inner class RecipeRecyclerViewHolder(private val binding: ItemRecipeSearchBinding) :
        BaseViewHolder<MealAndRecipeItem>(binding) {
        override fun bind(item: MealAndRecipeItem) = with(binding) {
            recipe = item.recipe
            btnDelete.visibility =
                if (isDeleting)
                    View.VISIBLE
                else
                    View.GONE
            btnDelete.setOnClickListener{
                showDeleteDialog(item)
            }
            root.setOnClickListener{
                context?.startActivity(
                    Intent(context, RecipeDetailActivity::class.java)
                        .putExtra(RecipeDetailActivity.KEY_CALL_FROM, RecipeDetailActivity.FROM_MEALS)
                        .putExtra(RecipeDetailActivity.KEY_RECIPE_ID,item.recipe.id)
                        .putExtra(RecipeDetailActivity.KEY_MEAL_ID,item.meal.id))
            }
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

    fun showDeleteDialog(item: MealAndRecipeItem)
    {
        context?.run{
            DialogUtil(context).showTwoBtn(
                title = context.getString(R.string.text_remove),
                content = context.getString(R.string.remove_recipe_item_dialog_content),
                confirmClickListener = {
                    viewModel.delete(item)
                }
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MealAndRecipeItem> {
        val view = ItemRecipeSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeRecyclerViewHolder(view)
    }
}