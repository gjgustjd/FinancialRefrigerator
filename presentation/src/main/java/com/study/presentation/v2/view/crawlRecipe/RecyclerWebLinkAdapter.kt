package com.study.presentation.v2.view.crawlRecipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.study.domain.model.WebLinkItem
import com.study.presentation.R
import com.study.presentation.databinding.ItemWebSearchBinding

class RecyclerWebLinkAdapter constructor(
    private var items: List<WebLinkItem> = listOf()
) : RecyclerView.Adapter<RecyclerWebLinkAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_web_search, parent, false)
        return Holder(ItemWebSearchBinding.bind(view))
    }

    fun setInsertItems(updatedItems: List<WebLinkItem>) {
        items = updatedItems
        notifyItemInserted(items.size)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.count()

    inner class Holder(val itemBinding: ItemWebSearchBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: WebLinkItem) {
            itemBinding.webLinkItem = item
            itemBinding.item.setOnClickListener{
            }
        }
    }
}
