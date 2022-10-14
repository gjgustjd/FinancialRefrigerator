package com.study.presentation.v2.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.study.presentation.old.presentation.Dialog.CustomProgressDialog
import kotlinx.coroutines.Job

abstract class BaseActivity<Binding: ViewDataBinding, VM: BaseViewModel>: AppCompatActivity() {
    lateinit var binding: Binding
    abstract val layoutId: Int
    abstract val viewModel: VM
    private lateinit var fetchJob: Job

    private var progressDialog: CustomProgressDialog? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, layoutId)
        super.onCreate(savedInstanceState)

        initViewModel()
        fetchJob = viewModel.fetchData()
        observeData()
    }

    open fun initViewModel() {}

    fun showProgressBar() {
        CustomProgressDialog.getInstance().also {
            progressDialog = it
        }.show(supportFragmentManager,"progressbar")
    }
    fun hideProgressBar() {
        progressDialog?.dismiss()
    }

    abstract fun observeData()


    override fun onDestroy() {
        if (fetchJob.isActive) {
            fetchJob.cancel()
        }
        super.onDestroy()
    }
}