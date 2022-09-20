package com.study.financialrefrigerator.presentation.recipe.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.base.BaseActivity
import com.study.financialrefrigerator.databinding.ActivityRecipeDetailBinding
import com.study.financialrefrigerator.model.recipe.RecipeItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailActivity : BaseActivity<ActivityRecipeDetailBinding, RecipeDetailViewModel>() {

    override val layoutId: Int
        get() = R.layout.activity_recipe_detail

    private lateinit var recipeItem: RecipeItem

    override val viewModel: RecipeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    override fun initViewModel() {
        viewModel.id = intent.getIntExtra("recipe_id", -1)
    }

    private fun initViews() {
        binding.titleBar.activity = this
    }

    override fun observeData() {
        viewModel.detailRecipe.observe(this) { recipeDetail ->
            when (recipeDetail) {
                is RecipeDetailState.UnInitialize -> {
                    initViews()
                }
                is RecipeDetailState.Loading -> {
                    //프로그래스 바 보임 처리
                }
                is RecipeDetailState.Success -> {
                    handleSuccessState(recipeDetail)
                }
                is RecipeDetailState.Delete -> {
                    viewModel.fetchData()
                    Toast.makeText(this, getString(R.string.delete_success), Toast.LENGTH_SHORT).show()
                }
                is RecipeDetailState.Error -> {
                    Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleSuccessState(recipeDetail: RecipeDetailState.Success) {
        recipeItem = recipeDetail.recipeDetail
        binding.titleBar.txtHomeTitle.text = recipeItem.name
        recipeItem.instruction = recipeItem.instruction.replace("\\n", System.lineSeparator())
        binding.recipe = recipeItem
        binding.btnAddRecipe.setOnClickListener {
            viewModel.addRecipe(recipeItem.id)
            onBackPressed()
            Toast.makeText(this, "할 요리에 추가되었습니다.", Toast.LENGTH_SHORT).show()
        }

    }
}