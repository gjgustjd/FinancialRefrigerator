package com.study.financialrefrigerator.presentation.recipe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.base.BaseFragment
import com.study.financialrefrigerator.databinding.FragmentRecipeBinding
import com.study.financialrefrigerator.presentation.view.ItemDecorate


class RecipeFragment : BaseFragment<FragmentRecipeBinding>() {

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

    private val viewModel: RecipeViewModel by viewModels()
    private val recipeRecyclerViewAdapter by lazy {
        RecipeRecyclerViewAdapter {

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initRecyclerView()
        viewModel.fetchData()
    }

    private fun initRecyclerView() {
        binding.recipeRecyclerView.adapter = recipeRecyclerViewAdapter
        binding.recipeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recipeRecyclerView.addItemDecoration(ItemDecorate())
    }

    private fun observeData() {
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
                    context?.let {
                        Toast.makeText(it, getString(R.string.delete_success), Toast.LENGTH_SHORT).show()
                    } ?: Log.d("RecipeFragment", "context null")
                }
                is RecipeState.Error -> {
                    context?.let {
                        Toast.makeText(it, getString(R.string.error), Toast.LENGTH_SHORT).show()
                    } ?: Log.d("RecipeFragment", "context null")
                }
            }
        }
    }

    private fun initViews() {

    }

    private fun handleSuccessState(recipeState: RecipeState.Success) {
        recipeRecyclerViewAdapter.submitList(recipeState.recipeList)
    }

}