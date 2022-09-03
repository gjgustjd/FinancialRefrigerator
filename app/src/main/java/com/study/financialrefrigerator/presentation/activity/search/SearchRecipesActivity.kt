package com.study.financialrefrigerator.presentation.activity.search

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.base.BaseActivity
import com.study.financialrefrigerator.databinding.ActivitySearchRecipesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchRecipesActivity : BaseActivity<ActivitySearchRecipesBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_search_recipes

    private val viewModel: SearchRecipesViewModel by viewModels()
    private val recipesAdapter by lazy {
        SearchRecipesAdapter(
            viewModel.recipes.value ?: listOf()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.recyclerSearchRecipes.adapter = recipesAdapter
        binding.recyclerSearchRecipes.layoutManager = LinearLayoutManager(baseContext)
        val ingredientsName = intent.getStringExtra("ingredient_name") ?: ""
        binding.titleBar.txtHomeTitle.text = """'$ingredientsName' 검색 결과"""
        viewModel.setupRecipesData(ingredientsName)
        viewModel.recipes.observe(this) {
            Log.i("recipesAdapter", it.toString())
            recipesAdapter.setItems(it)
        }
    }
}