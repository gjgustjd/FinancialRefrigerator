package com.study.financialrefrigerator.presentation.refrigerator.ingredients

import androidx.lifecycle.viewModelScope
import com.study.financialrefrigerator.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class IngredientsVieModel: BaseViewModel() {
    override fun fetchData(): Job = viewModelScope.launch {

    }
}