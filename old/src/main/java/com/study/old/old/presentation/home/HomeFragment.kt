package com.study.old.old.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.old.R
import com.study.old.old.presentation.base.BaseFragment
import com.study.old.databinding.FragmentHomeBinding
import com.study.old.old.model.ingredient.IngredientItem
import com.study.old.old.presentation.activity.search.SearchRecipesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModels()
    private val ingredientsAdapter by lazy { HomeIngredientsAdapter(
        requireActivity(),
        listOf(),
        object : DiffUtil.ItemCallback<IngredientItem>() {
            override fun areItemsTheSame(oldItem: IngredientItem, newItem: IngredientItem) = oldItem == newItem
            override fun areContentsTheSame(oldItem: IngredientItem, newItem: IngredientItem) = oldItem == newItem
        })
    }

    companion object {
        const val TAG = "HOME_FRAGMENT"
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        Log.i("HomeFragment","onResume")
    }

    override fun onResume() {
        super.onResume()
        viewModel.setupIngrediestsData()
        Log.i("HomeFragment","onResume")
    }

    private fun initViews()
    {
        binding.run{
            recyclerHomeIngredients.run{
                adapter = ingredientsAdapter
                layoutManager = LinearLayoutManager(context)
            }
            activity?.let { it ->
                viewModel.homeIngredients.observe(it)
                {
                    ingredientsAdapter.setItems(it)
                }
            }
            titleBar.run{
                imgbtnBack.visibility= GONE
                txtHomeTitle.text = "요리 검색"
            }
            edtHomeSearchRecipe.setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    startActivity(
                        Intent(activity, SearchRecipesActivity::class.java)
                            .putExtra("type", "recipe")
                            .putExtra("keyword", binding.edtHomeSearchRecipe.text.toString())
                    )
                }
                false
            }
        }
    }

    override fun observeData() {}
}