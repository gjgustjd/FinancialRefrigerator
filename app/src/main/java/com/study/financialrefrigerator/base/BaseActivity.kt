package com.study.financialrefrigerator.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlinx.coroutines.Job

abstract class BaseActivity<Binding: ViewDataBinding, VM:BaseViewModel>: AppCompatActivity() {
    lateinit var binding: Binding
    abstract val layoutId: Int
    abstract val viewModel: VM
    private lateinit var fetchJob: Job


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, layoutId)
        super.onCreate(savedInstanceState)

        initViewModel()
        fetchJob = viewModel.fetchData()
        observeData()
    }

    open fun initViewModel() {

    }

    abstract fun observeData()

    override fun onDestroy() {
        if (fetchJob.isActive) {
            fetchJob.cancel()
        }
        super.onDestroy()
    }
}