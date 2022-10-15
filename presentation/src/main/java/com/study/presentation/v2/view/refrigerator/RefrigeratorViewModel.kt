package com.study.presentation.v2.view.refrigerator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.domain.model.IngredientItem
import com.study.domain.usecase.local.ingredients.DeleteIngredientUseCase
import com.study.domain.usecase.local.ingredients.GetAllIngredientsUseCase
import com.study.presentation.v2.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RefrigeratorViewModel
@Inject constructor(
    private val getAllIngredientsUseCase: GetAllIngredientsUseCase,
    private val deleteIngredientUseCase: DeleteIngredientUseCase
    ) : BaseViewModel() {

    private val _refrigeratorLiveData = MutableLiveData<RefrigeratorState>(RefrigeratorState.UnInitialize)
    val refrigeratorLiveData: LiveData<RefrigeratorState> get() = _refrigeratorLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        _refrigeratorLiveData.postValue(RefrigeratorState.Loading)
            withContext(Dispatchers.IO) {
                getAllIngredientsUseCase.execute().collectLatest {
                    _refrigeratorLiveData.postValue(RefrigeratorState.Success(it))
                }
            }
    }

    fun delete(item: IngredientItem) {
        _refrigeratorLiveData.postValue(RefrigeratorState.Loading)
        deleteIngredientUseCase.invoke(viewModelScope, item)
        _refrigeratorLiveData.postValue(RefrigeratorState.Delete)

    }
}