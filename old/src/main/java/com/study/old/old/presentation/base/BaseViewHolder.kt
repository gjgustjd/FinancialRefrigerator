package com.study.old.old.presentation.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<Item>(itemView:ViewDataBinding):RecyclerView.ViewHolder(itemView.root) {
    abstract fun bind(item:Item)
}