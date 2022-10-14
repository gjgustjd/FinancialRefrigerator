package com.study.old.old.presentation.activity.main

import androidx.lifecycle.viewModelScope
import com.study.old.old.presentation.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel: BaseViewModel() {
    override fun fetchData(): Job = viewModelScope.launch {
    }
}