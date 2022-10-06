package com.study.financialrefrigerator.presentation.refrigerator.ingredients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.financialrefrigerator.presentation.base.BaseViewModel
import com.study.financialrefrigerator.model.RefriegeratorRepository
import com.study.financialrefrigerator.model.ingredient.IngredientItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientsVieModel @Inject constructor(private val repository: RefriegeratorRepository) : BaseViewModel() {

    private var _ingredientsLiveData = MutableLiveData<IngredientsState>(IngredientsState.UnInitialize)
    val ingredientsLiveData: LiveData<IngredientsState> = _ingredientsLiveData

    private var ingredientsId: Int? = null

    override fun fetchData(): Job = viewModelScope.launch {
        _ingredientsLiveData.postValue(IngredientsState.Loading)
        ingredientsId?.let {
            _ingredientsLiveData.postValue(IngredientsState.Success(repository.getIngredients(it)))
        }
    }

    fun setId(id: Int) {
        ingredientsId = id
    }

    fun writeTodo(item: IngredientItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertIngredients(item)
        }
        _ingredientsLiveData.postValue(IngredientsState.Write)
    }

    fun modifyTodo(item: IngredientItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateIngredients(item)
        }
        _ingredientsLiveData.postValue(IngredientsState.Modify)
    }
}