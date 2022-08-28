package com.study.financialrefrigerator.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.base.BaseFragment
import com.study.financialrefrigerator.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel: HomeViewModel by activityViewModels()

    companion object {
        const val TAG = "HOME_FRAGMENT"
    }

    override val layoutId: Int
        get() = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setupIngrediestsData()
    }

}