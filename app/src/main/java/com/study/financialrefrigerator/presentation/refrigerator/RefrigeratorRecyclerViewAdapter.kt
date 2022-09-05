package com.study.financialrefrigerator.presentation.refrigerator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.study.financialrefrigerator.databinding.RecipeItemBinding
import com.study.financialrefrigerator.databinding.RefrigeratorItemBinding

class RefrigeratorRecyclerViewAdapter :
    ListAdapter<RefrigeratorEntity, RefrigeratorRecyclerViewAdapter.RefrigeratorRecyclerViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<RefrigeratorEntity>() {
            override fun areItemsTheSame(oldItem: RefrigeratorEntity, newItem: RefrigeratorEntity): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: RefrigeratorEntity, newItem: RefrigeratorEntity): Boolean {
                return false
            }

        }
    }

    inner class RefrigeratorRecyclerViewHolder(private val binding: RefrigeratorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bindView(data:RefrigeratorEntity) = with(binding){
                name.text = data.name
                amount.text = data.amount
                day.text = "${data.day}일"
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefrigeratorRecyclerViewHolder {
        val view = RefrigeratorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RefrigeratorRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RefrigeratorRecyclerViewHolder, position: Int) {
        holder.bindView(currentList[position])
    }
}