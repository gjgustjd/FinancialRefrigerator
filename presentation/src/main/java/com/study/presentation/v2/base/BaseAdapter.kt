package com.study.presentation.v2.base

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.study.domain.model.DomainItem

abstract class BaseAdapter<Item : DomainItem, holder : BaseViewHolder<Item>> constructor(
    open val context: Context? = null,
    open var itemList: List<Item> = listOf(),
    diffUtil: DiffUtil.ItemCallback<Item> = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.getIdentifier() == newItem.getIdentifier()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
) : ListAdapter<Item, holder>(diffUtil) {
    open val asyncDiffUtil = AsyncListDiffer(this, diffUtil)

    fun setItems(updatedItems: List<Item>) {
        itemList = updatedItems.toMutableList()
        asyncDiffUtil.submitList(itemList)
    }

    override fun getItemCount() = itemList.count()

    override fun onBindViewHolder(holder: holder, position: Int) {
        holder.bind(itemList[holder.adapterPosition])
    }


}