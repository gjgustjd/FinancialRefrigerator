package com.study.financialrefrigerator.presentation.activity.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.financialrefrigerator.model.RefriegeratorRepository
import com.study.financialrefrigerator.model.recipe.RecipeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRecipesViewModel @Inject constructor(private val repository: RefriegeratorRepository):
    ViewModel() {
    private val _recipes = MutableLiveData<List<RecipeItem>>()
    val recipes:LiveData<List<RecipeItem>> = _recipes

    fun setupRecipesData(word:String) {
        viewModelScope.launch(Dispatchers.IO){
            _recipes.postValue(repository.getRecipe(word))
        }
    }
}