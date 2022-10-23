package com.study.presentation.v2.view.crawlRecipe

import com.study.domain.model.WebLinkItem

sealed class CrawlRecipeState {
    object Loading : CrawlRecipeState()
    data class Success(
        val item: WebLinkItem
    ) : CrawlRecipeState()
    object Error : CrawlRecipeState()
}