package com.study.financialrefrigerator.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.base.BaseActivity
import com.study.financialrefrigerator.databinding.ActivityMainBinding
import com.study.financialrefrigerator.presentation.home.HomeFragment
import com.study.financialrefrigerator.presentation.recipe.RecipeFragment
import com.study.financialrefrigerator.presentation.refrigerator.RefrigeratorFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_main

    private val homeFragment by lazy { HomeFragment() }
    private val recipeFragment by lazy { RecipeFragment() }
    private val refrigeratorFragment by lazy { RefrigeratorFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBottomNav()
        showFragment(homeFragment,HomeFragment.TAG)
    }

    private fun initBottomNav() {
        binding.run {
            bottomNav.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.home_menu -> {
                        showFragment(homeFragment, HomeFragment.TAG)
                        true
                    }
                    R.id.recipe_menu -> {
                        showFragment(recipeFragment, RecipeFragment.TAG)
                        true
                    }
                    R.id.refrigerator_menu -> {
                        showFragment(refrigeratorFragment,RefrigeratorFragment.TAG)
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        val findFragment = supportFragmentManager.findFragmentByTag(tag)
        supportFragmentManager.fragments.forEach { fm ->
            supportFragmentManager.beginTransaction().hide(fm).commit()
        }
        findFragment?.let {
            supportFragmentManager.beginTransaction().show(it).commit()
        } ?: kotlin.run {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, fragment, tag)
                .commitAllowingStateLoss()
        }

    }
}