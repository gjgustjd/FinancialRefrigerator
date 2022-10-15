package com.study.presentation.v2.view.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.domain.model.MealAndRecipeItem
import com.study.domain.repository.RefrigeratorRepository
import com.study.presentation.v2.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repository: RefrigeratorRepository) : BaseViewModel() {

    private var _recipeLiveData = MutableLiveData<RecipeState>(RecipeState.UnInitialize)
    val recipeLiveData: LiveData<RecipeState> get() = _recipeLiveData
    lateinit var mealsWithRecipesFlow: Flow<List<MealAndRecipeItem>>

    override fun fetchData(): Job  = viewModelScope.launch(Dispatchers.IO) {
        _recipeLiveData.postValue(RecipeState.Loading)
        withContext(Dispatchers.IO) {
            mealsWithRecipesFlow = repository.getAllMealsWithRecipeAsFlow()
            mealsWithRecipesFlow.collectLatest {
                _recipeLiveData.postValue(
                    RecipeState.Success(repository.getAllMealsWithRecipe())
                )
            }
        }
    }

    //delete 하는 아이템의 포지션을 받아 온 후 해당 포지션에 해당하는 db값을 지우는 로직 삽입
    // db값을 지운 값을 업데이트 하기 위해 다시 success(리스트값) 을 삽입.
    fun delete(item: MealAndRecipeItem) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteMeal(item.meal)
        _recipeLiveData.postValue(RecipeState.Delete)
    }



}