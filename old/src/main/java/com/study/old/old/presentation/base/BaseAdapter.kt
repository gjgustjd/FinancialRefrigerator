package com.study.old.old.presentation.base

import android.content.Context
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseAdapter<ItemVO:Any,holder: BaseViewHolder<ItemVO>> constructor(
    open val context: Context?=null,
    open var itemList: List<ItemVO>,
    diffUtil: DiffUtil.ItemCallback<ItemVO> = getDefaultDiffUtil()
) :    ListAdapter<ItemVO, holder>(diffUtil) {
    companion object{
        fun <ItemVO : Any> getDefaultDiffUtil() = object : DiffUtil.ItemCallback<ItemVO>() {
            override fun areItemsTheSame(oldItem: ItemVO, newItem: ItemVO) = oldItem == newItem
            override fun areContentsTheSame(oldItem: ItemVO, newItem: ItemVO) = false
        }
    }
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