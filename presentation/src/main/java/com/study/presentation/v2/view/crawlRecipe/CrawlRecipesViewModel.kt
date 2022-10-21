package com.study.presentation.v2.view.crawlRecipe

import androidx.lifecycle.viewModelScope
import com.study.domain.usecase.remote.GetCrawlRecipeWebLinkUseCase
import com.study.presentation.v2.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CrawlRecipesViewModel
@Inject constructor(
    private val getCrawlRecipeWebLinkUseCase: GetCrawlRecipeWebLinkUseCase
) :
    BaseViewModel() {

    suspend fun setupRecipesDataByIngredient(word: String) = getCrawlRecipeWebLinkUseCase.invoke(word, 100, 5)

    override fun fetchData(): Job = viewModelScope.launch {

    }
}