package com.study.financialrefrigerator.presentation.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.financialrefrigerator.model.RefriegeratorRepository
import com.study.financialrefrigerator.model.recipe.RecipeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repository: RefriegeratorRepository) : ViewModel() {

    private var _recipeLiveData = MutableLiveData<RecipeState>(RecipeState.UnInitialize)
    val recipeLiveData: LiveData<RecipeState> get() = _recipeLiveData

    fun fetchData() {
        _recipeLiveData.postValue(RecipeState.Loading)
        viewModelScope.launch {
            _recipeLiveData.postValue(
                RecipeState.Success(repository.getAllRecipe())
            )
        }
    }

    //delete 하는 아이템의 포지션을 받아 온 후 해당 포지션에 해당하는 db값을 지우는 로직 삽입
    // db값을 지운 값을 업데이트 하기 위해 다시 success(리스트값) 을 삽입.
    fun delete(item: RecipeItem) = viewModelScope.launch{
        repository.deleteRecipe(item)
        _recipeLiveData.postValue(RecipeState.Delete)
    }



}