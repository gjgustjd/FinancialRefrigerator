package com.study.financialrefrigerator.presentation.recipe.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.financialrefrigerator.base.BaseViewModel
import com.study.financialrefrigerator.model.RefriegeratorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(private val repository: RefriegeratorRepository) :
    BaseViewModel() {
    private val _detailRecipe = MutableLiveData<RecipeDetailState>(RecipeDetailState.UnInitialize)
    val detailRecipe: LiveData<RecipeDetailState> = _detailRecipe
    var id: Int = -1

    override fun fetchData(): Job = viewModelScope.launch {
        _detailRecipe.postValue(RecipeDetailState.Loading)
        _detailRecipe.postValue(RecipeDetailState.Success(repository.getRecipeById(id)))
    }

    fun addRecipe(recipeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMeal(recipeId)
            _detailRecipe.postValue(RecipeDetailState.Loading)
        }
    }



}