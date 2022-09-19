package com.study.financialrefrigerator.presentation.activity.search

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.base.BaseActivity
import com.study.financialrefrigerator.databinding.ActivitySearchRecipesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchRecipesActivity : BaseActivity<ActivitySearchRecipesBinding>() {
    companion object IntentKey{
       const val SEARCH_TYPE="type"
        const val SEARCH_KEYWORD="keyword"
        const val TYPE_INGREDIENT="ingredient"
        const val TYPE_RECIPE="recipe"
    }

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
        binding.run{
            val type = intent.getStringExtra(SEARCH_TYPE) ?: ""
            val keyword = intent.getStringExtra(SEARCH_KEYWORD) ?: ""

            recyclerSearchRecipes.run{
                adapter = recipesAdapter
                layoutManager = LinearLayoutManager(baseContext)
            }
            titleBar.run{
                activity = this@SearchRecipesActivity
                txtHomeTitle.text = getString(R.string.search_result,keyword)
            }

            when (type) {
                TYPE_INGREDIENT ->
                    viewModel.setupRecipesDataByIngredient(keyword)
                TYPE_RECIPE ->
                    viewModel.setupRecipesDataByName(keyword)
                else ->
                    Toast.makeText(this@SearchRecipesActivity, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.recipes.observe(this) {
            recipesAdapter.setItems(it)
        }
    }
}