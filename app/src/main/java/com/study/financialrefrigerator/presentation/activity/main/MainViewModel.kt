package com.study.financialrefrigerator.presentation.activity.main

import androidx.lifecycle.viewModelScope
import com.study.financialrefrigerator.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel: BaseViewModel() {
    override fun fetchData(): Job = viewModelScope.launch {
    }
}