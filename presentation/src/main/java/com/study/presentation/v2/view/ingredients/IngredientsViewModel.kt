package com.study.presentation.v2.view.ingredients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.domain.model.IngredientItem
import com.study.domain.usecase.ingredients.GetIngredientByIdUseCase
import com.study.domain.usecase.ingredients.InsertIngredientUseCase
import com.study.domain.usecase.ingredients.UpdateIngredientUseCase
import com.study.presentation.v2.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientsViewModel
@Inject constructor(
    private val getIngredientByIdUseCase: GetIngredientByIdUseCase,
    private val insertIngredientUseCase: InsertIngredientUseCase,
    private val updateIngredientUseCase: UpdateIngredientUseCase
) : BaseViewModel() {

    private var _ingredientsLiveData =
        MutableLiveData<IngredientsState>(IngredientsState.UnInitialize)
    val ingredientsLiveData: LiveData<IngredientsState> = _ingredientsLiveData

    private var ingredientsId: Int? = null

    override fun fetchData(): Job = viewModelScope.launch {
        _ingredientsLiveData.postValue(IngredientsState.Loading)
        ingredientsId?.let { id ->
            _ingredientsLiveData.postValue(
                IngredientsState.Success(
                    getIngredientByIdUseCase.invoke(
                        viewModelScope,
                        id
                    )
                )
            )
        }
    }

    fun setId(id: Int) {
        ingredientsId = id
    }

    fun writeTodo(item: IngredientItem) {
        insertIngredientUseCase.invoke(viewModelScope, item)
        _ingredientsLiveData.postValue(IngredientsState.Write)
    }

    fun modifyTodo(item: IngredientItem) {
        updateIngredientUseCase.invoke(viewModelScope, item)
        _ingredientsLiveData.postValue(IngredientsState.Modify)
    }
}