package com.study.presentation.v2.view.home

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.domain.model.IngredientItem
import com.study.presentation.R
import com.study.presentation.databinding.FragmentHomeBinding
import com.study.presentation.v2.base.BaseFragment
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
            binding.titleBar.run{
                imgbtnBack.visibility= GONE
                txtHomeTitle.text = "요리 검색"
            }
            edtHomeSearchRecipe.setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
//                    startActivity(
//                        Intent(activity, SearchRecipesActivity::class.java)
//                            .putExtra("type", "recipe")
//                            .putExtra("keyword", binding.edtHomeSearchRecipe.text.toString())
//                    )
                }
                false
            }
        }
    }

    override fun observeData() {}
}