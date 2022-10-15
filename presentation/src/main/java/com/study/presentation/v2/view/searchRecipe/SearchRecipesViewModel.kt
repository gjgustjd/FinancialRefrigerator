package com.study.presentation.v2.view.searchRecipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.domain.model.RecipeItem
import com.study.domain.usecase.recipe.GetRecipeByIngredientUseCase
import com.study.domain.usecase.recipe.GetRecipeByNameUseCase
import com.study.presentation.v2.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRecipesViewModel
@Inject constructor(
    private val getRecipeByNameUseCase: GetRecipeByNameUseCase,
    private val getRecipeByIngredientUseCase: GetRecipeByIngredientUseCase
) :
    BaseViewModel() {
    private val _recipes = MutableLiveData<List<RecipeItem>>()
    val recipes: LiveData<List<RecipeItem>> = _recipes

    fun setupRecipesDataByName(word: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _recipes.postValue(getRecipeByNameUseCase.invoke(viewModelScope,word))
        }
    }

    fun setupRecipesDataByIngredient(word: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _recipes.postValue(getRecipeByIngredientUseCase.invoke(viewModelScope,word))
        }
    }

    override fun fetchData(): Job = viewModelScope.launch {

    }
}