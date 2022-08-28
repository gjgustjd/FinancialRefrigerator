package com.study.financialrefrigerator.presentation.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.financialrefrigerator.model.RefriegeratorRepository
import com.study.financialrefrigerator.model.ingredient.IngredientItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RefriegeratorRepository):
    ViewModel() {
    private val _homeIngredients = MutableLiveData<List<IngredientItem>>()
    val homeIngredients by lazy { _homeIngredients.value }

    fun setupIngrediestsData() {
        viewModelScope.launch {
            _homeIngredients.value = repository.getAllIngredient()
            Log.i("HomeViewModel", _homeIngredients.value.toString())
        }
    }

}