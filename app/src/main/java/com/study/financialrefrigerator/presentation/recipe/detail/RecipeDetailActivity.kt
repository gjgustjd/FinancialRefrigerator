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
    lateinit var call_from:String

    companion object{
        const val KEY_CALL_FROM = "key_call_from"
        const val KEY_RECIPE_ID = "key_recipe_id"
        const val FROM_MEALS="from meals"
        const val FROM_HOME="from home"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    override fun initViewModel() {
        viewModel.id = intent.getIntExtra(KEY_RECIPE_ID, -1)
    }

    private fun initViews() {
        binding.titleBar.activity = this
        call_from = intent.getStringExtra(KEY_CALL_FROM)?:throw IllegalStateException()

        binding.btnAddRecipe.text =
            when (call_from) {
                FROM_HOME -> {
                    getString(R.string.add_to_cook)
                }
                FROM_MEALS -> {
                    getString(R.string.complete_cook)
                }
                else -> throw IllegalStateException()
            }
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
            val recipeId = recipeItem.id
            val toastTextResource =
                when (call_from) {
                    FROM_HOME -> {
                        viewModel.addRecipe(recipeId)
                        R.string.toast_added_meal
                    }
                    FROM_MEALS -> {
                        viewModel.deleteMealWithRecipeId(recipeId)
                        R.string.delete_success
                    }
                    else -> throw IllegalStateException()
                }

            onBackPressed()
            Toast.makeText(this, getString(toastTextResource), Toast.LENGTH_SHORT).show()
        }

    }
}