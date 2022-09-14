package com.study.financialrefrigerator.presentation.activity.recipeDetail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.base.BaseActivity
import com.study.financialrefrigerator.databinding.ActivityRecipeDetailBinding
import com.study.financialrefrigerator.databinding.ActivitySearchRecipesBinding
import com.study.financialrefrigerator.model.recipe.RecipeItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailActivity : BaseActivity<ActivityRecipeDetailBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_recipe_detail

    private lateinit var recipeItem: RecipeItem

    private val viewModel: RecipeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        viewModel.setupRecipesDataById(intent.getIntExtra("recipe_id",0))
        viewModel.recipe.observe(this){
            recipeItem = it
            binding.titleBar.txtHomeTitle.text = recipeItem.name
            binding.recipe = recipeItem
            binding.btnAddRecipe.setOnClickListener{
                viewModel.addRecipe(recipeItem.id)
                onBackPressed()
                Toast.makeText(this,"할 요리에 추가되었습니다.",Toast.LENGTH_SHORT).show()
            }
        }

    }
}