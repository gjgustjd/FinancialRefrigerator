package com.study.financialrefrigerator.presentation.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.financialrefrigerator.model.RefriegeratorRepository
import com.study.financialrefrigerator.model.ingredient.IngredientItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RefriegeratorRepository):
    ViewModel() {
    private val _homeIngredients = MutableLiveData<List<IngredientItem>>()
    val homeIngredients:LiveData<List<IngredientItem>> =  _homeIngredients

    fun setupIngrediestsData() {
        viewModelScope.launch {
            _homeIngredients.value = repository.getAllIngredient()
        }
    }

}