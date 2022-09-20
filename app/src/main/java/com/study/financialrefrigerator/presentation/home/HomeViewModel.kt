package com.study.financialrefrigerator.presentation.home

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
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RefriegeratorRepository):
    BaseViewModel() {
    private val _homeIngredients = MutableLiveData<List<IngredientItem>>()
    val homeIngredients:LiveData<List<IngredientItem>> =  _homeIngredients

    fun setupIngrediestsData() {
        viewModelScope.launch(Dispatchers.IO) {
            _homeIngredients.postValue(repository.getAllIngredient())
        }
    }

    override fun fetchData(): Job = viewModelScope.launch {
    }

}