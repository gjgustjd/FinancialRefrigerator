package com.study.presentation.v2.view.refrigerator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.study.domain.model.IngredientItem
import com.study.presentation.databinding.RefrigeratorItemBinding

class RefrigeratorRecyclerViewAdapter(
    private val itemOnClicked: (IngredientItem) -> Unit,
    private val deleteOnClicked: (IngredientItem) -> Unit
) :
    ListAdapter<IngredientItem, RefrigeratorRecyclerViewAdapter.RefrigeratorRecyclerViewHolder>(
        diffUtil
    ) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<IngredientItem>() {
            override fun areItemsTheSame(
                oldItem: IngredientItem,
                newItem: IngredientItem
            ): Boolean {
                return false
            }

            override fun areContentsTheSame(
                oldItem: IngredientItem,
                newItem: IngredientItem
            ): Boolean {
                return false
            }

        }
    }

    inner class RefrigeratorRecyclerViewHolder(private val binding: RefrigeratorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(data: IngredientItem) = with(binding) {
            name.text = data.name
            amount.text = data.quantity.toString()
            day.text = "${data.shelf_life}일"

            itemView.setOnClickListener {
                itemOnClicked(data)
            }
            binding.deleteButton.setOnClickListener {
                deleteOnClicked(data)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RefrigeratorRecyclerViewHolder {
        val view =
            RefrigeratorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RefrigeratorRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RefrigeratorRecyclerViewHolder, position: Int) {
        holder.bindView(currentList[position])
    }
}