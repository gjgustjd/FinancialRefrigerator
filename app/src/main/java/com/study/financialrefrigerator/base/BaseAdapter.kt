package com.study.financialrefrigerator.base

import android.content.Context
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<ItemVO:Any,holder:RecyclerView.ViewHolder> constructor(
    protected val context: Context,
    open var itemList: List<ItemVO>,
    diffUtil: DiffUtil.ItemCallback<ItemVO> = object : DiffUtil.ItemCallback<ItemVO>() {
        override fun areItemsTheSame(oldItem: ItemVO, newItem: ItemVO) = oldItem == newItem
        override fun areContentsTheSame(oldItem: ItemVO, newItem: ItemVO) = false
    }
) :    ListAdapter<ItemVO, holder>(diffUtil) {
    open val asyncDiffUtil = AsyncListDiffer(this,diffUtil)

    fun setItems(updatedItems: List<ItemVO>) {
        itemList = updatedItems
        asyncDiffUtil.submitList(itemList)
    }
    override fun getItemCount() = itemList.count()
}