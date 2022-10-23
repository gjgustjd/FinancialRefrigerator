package com.study.presentation.v2.view.crawlRecipe

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.study.domain.model.WebLinkItem
import com.study.presentation.R
import com.study.presentation.databinding.ItemWebSearchBinding
import com.study.presentation.v2.base.BaseAdapter
import com.study.presentation.v2.base.BaseViewHolder
import com.study.presentation.v2.view.recipeDetail.RecipeDetailActivity

class RecyclerWebLinkAdapter constructor(
    context: Context,
    itemList: List<WebLinkItem> = listOf(),
) : BaseAdapter<WebLinkItem, BaseViewHolder<WebLinkItem>>(context, itemList,
    object : DiffUtil.ItemCallback<WebLinkItem>() {
        override fun areItemsTheSame(oldItem: WebLinkItem, newItem: WebLinkItem) =
            oldItem.href === newItem.href

        override fun areContentsTheSame(oldItem: WebLinkItem, newItem: WebLinkItem) =
            oldItem == newItem

    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<WebLinkItem> {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_web_search, parent, false)
        return Holder(ItemWebSearchBinding.bind(view))
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class Holder(private val itemBinding: ItemWebSearchBinding) :
        BaseViewHolder<WebLinkItem>(itemBinding) {
        override fun bind(item: WebLinkItem) = with(itemBinding) {
            webLinkItem = item
            this.item.setOnClickListener {
                context?.startActivity(
                    Intent(context,RecipeDetailActivity::class.java)
                        .putExtra(RecipeDetailActivity.KEY_CALL_FROM,RecipeDetailActivity.FROM_HOME)
                        .putExtra(RecipeDetailActivity.KEY_RECIPE_URL,item.href)
                )
            }
        }
    }
}
