package com.study.presentation.v2.view.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.study.presentation.R
import com.study.presentation.v2.base.BaseActivity
import com.study.presentation.databinding.ActivityMainBinding
import com.study.presentation.v2.view.home.HomeFragment
import com.study.presentation.v2.view.recipe.RecipeFragment
import com.study.presentation.v2.view.refrigerator.RefrigeratorFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutId: Int
        get() = R.layout.activity_main

    override val viewModel: MainViewModel by viewModels()

    private val homeFragment by lazy { HomeFragment.newInstance() }
    private val recipeFragment by lazy { RecipeFragment.newInstance() }
    private val refrigeratorFragment by lazy { RefrigeratorFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBottomNav()
        showFragment(homeFragment, HomeFragment.TAG)
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
                        showFragment(refrigeratorFragment, RefrigeratorFragment.TAG)
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

    override fun observeData() {
    }
}