package com.study.domain.model

data class WebLinkItem(
    val href: String,
    val imageUrl: String,
    val title: String,
    val desc: String
) : DomainItem() {
    override fun getIdentifier(): String {
        return href
    }
}