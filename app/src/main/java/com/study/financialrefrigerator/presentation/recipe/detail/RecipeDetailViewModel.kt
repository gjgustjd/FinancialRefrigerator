package com.study.financialrefrigerator.presentation.recipe.detail

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
class RecipeDetailViewModel @Inject constructor(private val repository: RefriegeratorRepository):
    ViewModel() {
    private val _recipe = MutableLiveData<RecipeItem>()
    val recipe:LiveData<RecipeItem> = _recipe

    fun setupRecipesDataById(id:Int) {
        viewModelScope.launch(Dispatchers.IO){
            _recipe.postValue(repository.getRecipeById(id))
        }
    }

    fun addRecipe(recipeId:Int)
    {
       viewModelScope.launch(Dispatchers.IO){
           repository.insertMeal(recipeId)
       }
    }
}