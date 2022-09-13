package com.study.financialrefrigerator.presentation.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.databinding.RecipeItemBinding

class RecipeRecyclerViewAdapter(private val itemOnClicked: (RecipeEntity) -> Unit) :
    ListAdapter<RecipeEntity, RecipeRecyclerViewAdapter.RecipeRecyclerViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<RecipeEntity>() {
            override fun areItemsTheSame(oldItem: RecipeEntity, newItem: RecipeEntity): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: RecipeEntity, newItem: RecipeEntity): Boolean {
                return false
            }

        }
    }

    inner class RecipeRecyclerViewHolder(private val binding: RecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bindView(data:RecipeEntity) = with(binding){
                recipeName.text = data.name
                recipeIngredients.text = data.subName
                recipeTime.text = data.Time
                recipeImageView.setImageResource(R.drawable.ic_launcher_background)


                itemView.setOnClickListener{
                    itemOnClicked
                }

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