package com.study.presentation.v2.view.crawlRecipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.study.domain.model.WebLinkItem
import com.study.domain.usecase.remote.GetCrawlRecipeWebLinkUseCase
import com.study.presentation.v2.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CrawlRecipesViewModel
@Inject constructor(
    private val getCrawlRecipeWebLinkUseCase: GetCrawlRecipeWebLinkUseCase
) : BaseViewModel() {
    var _webLinks: LiveData<WebLinkItem> = MutableLiveData()

    suspend fun setupRecipesDataByIngredient(word: String) {
        _webLinks = getCrawlRecipeWebLinkUseCase
            .invoke(word, 100, 5)
            .buffer()
            .asLiveData()
    }

    override fun fetchData(): Job = viewModelScope.launch {

    }
}