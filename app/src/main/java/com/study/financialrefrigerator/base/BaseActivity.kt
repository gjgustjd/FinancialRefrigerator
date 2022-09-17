package com.study.financialrefrigerator.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<Binding: ViewDataBinding>: AppCompatActivity() {
    lateinit var binding: Binding
    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, layoutId)
        super.onCreate(savedInstanceState)
    }

}