package com.study.financialrefrigerator.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseAdapter<ItemVO:Any,holder:BaseViewHolder<ItemVO>> constructor(
    open val context: Context?=null,
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

    override fun onBindViewHolder(holder: holder, position: Int) {
        holder.bind(itemList[position])
    }


}