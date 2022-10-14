package com.study.presentation.v2.view.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.domain.model.IngredientItem
import com.study.domain.repository.RefriegeratorRepository
import com.study.presentation.v2.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RefriegeratorRepository):
    BaseViewModel() {
    private val _homeIngredients = MutableLiveData<List<IngredientItem>>()
    val homeIngredients:LiveData<List<IngredientItem>> =  _homeIngredients
    lateinit var homeIngredientFlow: Flow<List<IngredientItem>>

    fun setupIngrediestsData() {
        viewModelScope.launch(Dispatchers.IO) {
            homeIngredientFlow = repository.getAllIngredientByFlow()
            homeIngredientFlow.collectLatest {
                _homeIngredients.postValue(it)
            }
        }
    }

    override fun fetchData(): Job = viewModelScope.launch {
    }

}