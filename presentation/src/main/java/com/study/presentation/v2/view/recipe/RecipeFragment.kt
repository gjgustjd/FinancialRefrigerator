package com.study.presentation.v2.view.recipe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.presentation.R
import com.study.presentation.databinding.FragmentRecipeBinding
import com.study.presentation.v2.base.BaseFragment
import com.study.presentation.v2.view.ingredients.IngredientsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeFragment : BaseFragment<FragmentRecipeBinding, RecipeViewModel>() {

    companion object {
        const val TAG = "RECIPE_FRAGMENT"
        fun newInstance(): RecipeFragment {
            val args = Bundle()
            val fragment = RecipeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_recipe

    override val viewModel: RecipeViewModel by viewModels()

    private val recipeRecyclerViewAdapter by lazy {
        RecipeRecyclerViewAdapter(
            viewModel = viewModel,
            context = context ?: throw IllegalArgumentException()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initTitleView()
        initRecyclerView()
        initFloatingButton()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData()
    }
    private fun initFloatingButton(){
        binding.floatBtnSearchRecipe.setOnClickListener {
            context?.let {
            }
        }
    }

    private fun initRecyclerView() {
        binding.recipeRecyclerView.adapter = recipeRecyclerViewAdapter
        binding.recipeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initTitleView() = with(binding.titleBar)
    {
        txtHomeTitle.text = getString(R.string.title_meals)
        imgbtnBack.visibility = View.GONE
        btnAction.visibility = View.VISIBLE
        btnAction.setOnClickListener {
            if(recipeRecyclerViewAdapter.isDeleting)
            {
                btnAction.text = getString(R.string.text_remove)
                recipeRecyclerViewAdapter.isDeleting = false
            }
            else
            {
                btnAction.text = getString(R.string.text_cancel)
                recipeRecyclerViewAdapter.isDeleting = true
            }

            recipeRecyclerViewAdapter.refreshData()
        }
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.mealsWithRecipesFlow.collectLatest {
                if (it.isEmpty()) {
                    btnAction.visibility = View.GONE
                    binding.txtEmptyRecipes.visibility = View.VISIBLE
                } else {
                    btnAction.visibility = View.VISIBLE
                    binding.txtEmptyRecipes.visibility = View.GONE
                }
            }
        }
    }

    override fun observeData() {
        viewModel.recipeLiveData.observe(viewLifecycleOwner) { recipeState ->
            when (recipeState) {
                is RecipeState.UnInitialize -> {
                    initViews()
                }
                is RecipeState.Loading -> {
                    //프로그래스 바 보임 처리
                }
                is RecipeState.Success -> {
                    handleSuccessState(recipeState)
                }
                is RecipeState.Delete -> {
                    viewModel.fetchData()
                    Toast.makeText(requireContext(), getString(R.string.delete_success), Toast.LENGTH_SHORT).show()
                }
                is RecipeState.Error -> {
                    Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initViews() {

    }

    override fun onStop() {
        super.onStop()
        Log.i("RecipeFragment","onStop")
    }

    private fun handleSuccessState(recipeState: RecipeState.Success) {
        recipeRecyclerViewAdapter.setItems(recipeState.recipeList)
    }

}