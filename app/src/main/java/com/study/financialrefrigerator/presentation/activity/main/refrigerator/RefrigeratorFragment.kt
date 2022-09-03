package com.study.financialrefrigerator.presentation.activity.main.refrigerator

import android.os.Bundle
import android.view.View
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.base.BaseFragment
import com.study.financialrefrigerator.databinding.FragmentRefrigeratorBinding

class RefrigeratorFragment : BaseFragment<FragmentRefrigeratorBinding>() {

    companion object {
        const val TAG = "REFRIGERATOR_FRAGMENT"
    }

    override val layoutId: Int
        get() = R.layout.fragment_refrigerator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}