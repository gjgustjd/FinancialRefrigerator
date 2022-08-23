package com.study.financialrefrigerator.presentation.recipe

import android.os.Bundle
import android.view.View
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.base.BaseFragment
import com.study.financialrefrigerator.databinding.FragmentRecipeBinding


class RecipeFragment : BaseFragment<FragmentRecipeBinding>() {

    companion object {
        const val TAG = "RECIPE_FRAGMENT"
    }

    override val layoutId: Int
        get() = R.layout.fragment_recipe

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}