package com.study.financialrefrigerator.presentation.refrigerator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.financialrefrigerator.model.RefriegeratorRepository
import com.study.financialrefrigerator.model.ingredient.IngredientItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RefrigeratorViewModel @Inject constructor(private val repository: RefriegeratorRepository) : ViewModel() {

    private val _refrigeratorLiveData = MutableLiveData<RefrigeratorState>(RefrigeratorState.UnInitialize)
    val refrigeratorLiveData: LiveData<RefrigeratorState> get() = _refrigeratorLiveData

    fun fetchData() {
        _refrigeratorLiveData.postValue(RefrigeratorState.Loading)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _refrigeratorLiveData.postValue(RefrigeratorState.Success(repository.getAllIngredient()))
            }
        }
    }

    fun delete(item: IngredientItem) {
        repository.deleteIngredients(item)
        _refrigeratorLiveData.postValue(RefrigeratorState.Delete)
    }

}