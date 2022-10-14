package com.study.presentation.v2.view.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.domain.model.IngredientItem
import com.study.domain.usecase.ingredients.GetAllIngredientsUseCase
import com.study.presentation.v2.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getAllIngredientsUseCase: GetAllIngredientsUseCase):
    BaseViewModel() {
    private val _homeIngredients = MutableLiveData<List<IngredientItem>>()
    val homeIngredients: LiveData<List<IngredientItem>> = _homeIngredients

    fun setupIngrediestsData() {
        getAllIngredientsUseCase
            .invoke(viewModelScope)
            {
                _homeIngredients.postValue(it)
            }
    }

    override fun fetchData(): Job = viewModelScope.launch {
    }

}