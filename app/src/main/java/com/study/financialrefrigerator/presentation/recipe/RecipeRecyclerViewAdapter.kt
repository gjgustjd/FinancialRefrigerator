package com.study.financialrefrigerator.presentation.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.databinding.RecipeItemBinding
import com.study.financialrefrigerator.model.recipe.RecipeItem

class RecipeRecyclerViewAdapter(private val itemOnClicked: (RecipeEntity) -> Unit) :
    ListAdapter<RecipeEntity, RecipeRecyclerViewAdapter.RecipeRecyclerViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<RecipeItem>() {
            override fun areItemsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
                return false
            }

        }
    }

    inner class RecipeRecyclerViewHolder(private val binding: RecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bindView(data: RecipeItem) = with(binding){
                recipeName.text = data.name
                recipeIngredients.text = data.ingredients
                recipeTime.text = data.time.toString()
                recipeImageView.setImageResource(R.drawable.ic_launcher_background) // Glide로 이미지 URL 로딩으로 변경
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeRecyclerViewHolder {
        val view = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeRecyclerViewHolder, position: Int) {
        holder.bindView(currentList[position])
    }
}