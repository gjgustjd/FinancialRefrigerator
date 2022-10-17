package com.study.presentation.v2.view.crawlRecipe

import androidx.lifecycle.viewModelScope
import com.study.domain.model.WebLinkItem
import com.study.domain.usecase.remote.GetCrawlRecipeWebLinkUseCase
import com.study.presentation.v2.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CrawlRecipesViewModel
@Inject constructor(
    private val getCrawlRecipeWebLinkUseCase: GetCrawlRecipeWebLinkUseCase
) :
    BaseViewModel() {
    var webLinks: Flow<WebLinkItem> = flowOf()


    fun setupRecipesDataByIngredient(word: String) {
        viewModelScope.launch(Dispatchers.IO) {
            webLinks = getCrawlRecipeWebLinkUseCase.invoke(word, 100, 5)
        }
    }

    override fun fetchData(): Job = viewModelScope.launch {

    }
}