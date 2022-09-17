package com.study.financialrefrigerator.presentation.activity.search

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
            this,
            viewModel.recipes.value ?: listOf()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.titleBar.activity = this
        val type = intent.getStringExtra("type") ?: ""
        val keyword = intent.getStringExtra("keyword") ?: ""

        binding.recyclerSearchRecipes.adapter = recipesAdapter
        binding.recyclerSearchRecipes.layoutManager = LinearLayoutManager(baseContext)
        binding.titleBar.txtHomeTitle.text = "'$keyword' 검색 결과"

        if (type == "ingredient")
            viewModel.setupRecipesDataByIngredient(keyword)
        else if(type=="recipe")
            viewModel.setupRecipesDataByName(keyword)
        else
            Toast.makeText(this,"오류가 발생했습니다.",Toast.LENGTH_SHORT).show()

        viewModel.recipes.observe(this) {
            Log.i("recipesAdapter", it.toString())
            recipesAdapter.setItems(it)
        }
    }
}