package com.study.presentation.v2.view.main

import androidx.lifecycle.viewModelScope
import com.study.presentation.v2.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel: BaseViewModel() {
    override fun fetchData(): Job = viewModelScope.launch {
    }
}