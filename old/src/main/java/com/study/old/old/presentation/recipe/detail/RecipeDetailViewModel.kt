package com.study.old.old.presentation.recipe.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.old.old.presentation.base.BaseViewModel
import com.study.old.old.model.RefriegeratorRepository
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

    fun deleteMealWithId(mealId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMealWithId(mealId)
            _detailRecipe.postValue(RecipeDetailState.Loading)
        }
    }

}