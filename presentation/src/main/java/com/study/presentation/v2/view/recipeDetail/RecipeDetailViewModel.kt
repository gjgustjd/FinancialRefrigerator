package com.study.presentation.recipe.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.domain.repository.RefrigeratorRepository
import com.study.domain.usecase.local.meal.DeleteMealByIdUseCase
import com.study.domain.usecase.local.meal.InsertMealUseCase
import com.study.domain.usecase.local.recipe.GetRecipeByIdUseCase
import com.study.presentation.v2.base.BaseViewModel
import com.study.presentation.v2.view.recipeDetail.RecipeDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel
@Inject constructor(
    private val insertMealUseCase: InsertMealUseCase,
    private val deleteMealByIdUseCase: DeleteMealByIdUseCase,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase
) : BaseViewModel() {
    private val _detailRecipe = MutableLiveData<RecipeDetailState>(RecipeDetailState.UnInitialize)
    val detailRecipe: LiveData<RecipeDetailState> = _detailRecipe
    var id: Int = -1

    override fun fetchData(): Job = viewModelScope.launch {
        _detailRecipe.postValue(RecipeDetailState.Loading)
        _detailRecipe.postValue(RecipeDetailState.Success(getRecipeByIdUseCase.invoke(viewModelScope,id)))
    }

    fun addRecipe(recipeId: Int) {
        insertMealUseCase.invoke(viewModelScope, recipeId)
        _detailRecipe.postValue(RecipeDetailState.Loading)
    }

    fun deleteMealWithId(mealId: Int) {
        deleteMealByIdUseCase.invoke(viewModelScope, mealId)
        _detailRecipe.postValue(RecipeDetailState.Loading)
    }

}