package com.study.financialrefrigerator.presentation.refrigerator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RefrigeratorViewModel: ViewModel() {

    private val _refrigeratorLiveData = MutableLiveData<RefrigeratorState>(RefrigeratorState.UnInitialize)
    val refrigeratorLiveData:LiveData<RefrigeratorState> get() = _refrigeratorLiveData

    fun fetchData(){
        _refrigeratorLiveData.postValue(RefrigeratorState.Loading)
        _refrigeratorLiveData.postValue(RefrigeratorState.Success(listOf(RefrigeratorEntity("다짐육","300g",26),
            RefrigeratorEntity("대파","20g",10),RefrigeratorEntity("당근","1개",5),RefrigeratorEntity("피망","200g",15))))
    }

}