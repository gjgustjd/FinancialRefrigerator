package com.study.presentation.v2.view.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.domain.model.MealAndRecipeItem
import com.study.domain.usecase.meal.DeleteMealUseCase
import com.study.domain.usecase.meal.GetAllMealsWithRecipeUseCase
import com.study.presentation.v2.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel
@Inject constructor(
    private val getAllMealsWithRecipeUseCase: GetAllMealsWithRecipeUseCase,
    private val deleteRecipeUseCase: DeleteMealUseCase
) : BaseViewModel() {
    private var _recipeLiveData = MutableLiveData<RecipeState>(RecipeState.UnInitialize)
    val recipeLiveData: LiveData<RecipeState> get() = _recipeLiveData
    lateinit var mealsWithRecipesFlow: Flow<List<MealAndRecipeItem>>

    override fun fetchData(): Job  = viewModelScope.launch(Dispatchers.IO) {
        _recipeLiveData.postValue(RecipeState.Loading)
        mealsWithRecipesFlow = getAllMealsWithRecipeUseCase.invoke()
        mealsWithRecipesFlow.collectLatest {
            _recipeLiveData.postValue(RecipeState.Success(it))
        }
    }

    //delete 하는 아이템의 포지션을 받아 온 후 해당 포지션에 해당하는 db값을 지우는 로직 삽입
    // db값을 지운 값을 업데이트 하기 위해 다시 success(리스트값) 을 삽입.
    fun delete(item: MealAndRecipeItem) = viewModelScope.launch(Dispatchers.IO){
        deleteRecipeUseCase.invoke(viewModelScope,item.meal)
        _recipeLiveData.postValue(RecipeState.Delete)
    }
}