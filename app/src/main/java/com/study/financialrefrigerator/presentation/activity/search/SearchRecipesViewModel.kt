package com.study.financialrefrigerator.presentation.activity.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.financialrefrigerator.base.BaseViewModel
import com.study.financialrefrigerator.model.RefriegeratorRepository
import com.study.financialrefrigerator.model.recipe.RecipeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRecipesViewModel @Inject constructor(private val repository: RefriegeratorRepository) :
    BaseViewModel() {
    private val _recipes = MutableLiveData<List<RecipeItem>>()
    val recipes: LiveData<List<RecipeItem>> = _recipes

    fun setupRecipesDataByName(word: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _recipes.postValue(repository.getRecipeByName(word))
        }
    }

    fun setupRecipesDataByIngredient(word: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _recipes.postValue(repository.getRecipeByIngredients(word))
        }
    }

    override fun fetchData(): Job = viewModelScope.launch {

    }
}