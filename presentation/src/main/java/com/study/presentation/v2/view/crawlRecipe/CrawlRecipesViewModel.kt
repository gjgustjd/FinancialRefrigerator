package com.study.presentation.v2.view.crawlRecipe

import androidx.lifecycle.viewModelScope
import com.study.domain.usecase.remote.GetCrawlRecipeWebLinkUseCase
import com.study.presentation.v2.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CrawlRecipesViewModel
@Inject constructor(
    private val getCrawlRecipeWebLinkUseCase: GetCrawlRecipeWebLinkUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow<CrawlRecipeState>(CrawlRecipeState.Loading)
    val uiState = _uiState.asStateFlow()

    fun setupRecipesDataByIngredient(word: String) {
        viewModelScope.launch {
            getCrawlRecipeWebLinkUseCase
                .invoke(word, 100, 5)
                .flowOn(Dispatchers.IO)
                .buffer()
                .catch { _uiState.value = CrawlRecipeState.Error }
                .collect {
                    _uiState.value = CrawlRecipeState.Success(it)
                }
        }
    }

    override fun fetchData(): Job = viewModelScope.launch {

    }
}