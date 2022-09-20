package com.study.financialrefrigerator.presentation.recipe

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.base.BaseFragment
import com.study.financialrefrigerator.databinding.FragmentRecipeBinding
import dagger.hilt.android.AndroidEntryPoint

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
        RecipeRecyclerViewAdapter()
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

    private fun handleSuccessState(recipeState: RecipeState.Success) {
        recipeRecyclerViewAdapter.submitList(recipeState.recipeList)
    }

}