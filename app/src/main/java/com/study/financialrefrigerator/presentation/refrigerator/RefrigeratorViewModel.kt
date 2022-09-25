package com.study.financialrefrigerator.presentation.refrigerator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.financialrefrigerator.base.BaseViewModel
import com.study.financialrefrigerator.model.RefriegeratorRepository
import com.study.financialrefrigerator.model.ingredient.IngredientItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RefrigeratorViewModel @Inject constructor(private val repository: RefriegeratorRepository) : BaseViewModel() {

    private val _refrigeratorLiveData = MutableLiveData<RefrigeratorState>(RefrigeratorState.UnInitialize)
    val refrigeratorLiveData: LiveData<RefrigeratorState> get() = _refrigeratorLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        _refrigeratorLiveData.postValue(RefrigeratorState.Loading)
            withContext(Dispatchers.IO) {
                _refrigeratorLiveData.postValue(RefrigeratorState.Success(repository.getAllIngredient()))
            }
    }

    fun delete(item: IngredientItem) {
        _refrigeratorLiveData.postValue(RefrigeratorState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteIngredients(item)
        }
        _refrigeratorLiveData.postValue(RefrigeratorState.Delete)

    }

}