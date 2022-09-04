package com.study.financialrefrigerator.presentation.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecipeViewModel: ViewModel() {

    private var _recipeLiveData = MutableLiveData<RecipeState>(RecipeState.UnInitialize)
    val recipeLiveData:LiveData<RecipeState> get() = _recipeLiveData

    fun fetchData(){
        _recipeLiveData.postValue(RecipeState.Loading)
        _recipeLiveData.postValue(RecipeState.Success(listOf())) // todo repository 에서 요리 계획 리스트를 반환하는 함수를 삽
    }

    //delete 하는 아이템의 포지션을 받아 온 후 해당 포지션에 해당하는 db값을 지우는 로직 삽입
    // db값을 지운 값을 업데이트 하기 위해 다시 success(리스트값) 을 삽입.
    fun delete() = viewModelScope.launch{
        _recipeLiveData.postValue(RecipeState.Delete)

    }



}