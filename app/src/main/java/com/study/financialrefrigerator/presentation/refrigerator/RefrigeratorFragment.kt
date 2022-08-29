package com.study.financialrefrigerator.presentation.refrigerator

import android.os.Bundle
import android.view.View
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.base.BaseFragment
import com.study.financialrefrigerator.databinding.FragmentRefrigeratorBinding
import com.study.financialrefrigerator.presentation.recipe.RecipeFragment

class RefrigeratorFragment : BaseFragment<FragmentRefrigeratorBinding>() {

    companion object {
        const val TAG = "REFRIGERATOR_FRAGMENT"
        fun newInstance(): RefrigeratorFragment {
            val args = Bundle()
            val fragment = RefrigeratorFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_refrigerator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}