package com.study.financialrefrigerator.presentation.activity.main.home

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.base.BaseFragment
import com.study.financialrefrigerator.databinding.FragmentHomeBinding
import com.study.financialrefrigerator.presentation.activity.search.SearchRecipesActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel: HomeViewModel by activityViewModels()
    private val ingredientsAdapter by lazy{HomeIngredientsAdapter(requireActivity(), listOf())}

    companion object {
        const val TAG = "HOME_FRAGMENT"
        fun newInstance():HomeFragment  {
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
        viewModel.setupIngrediestsData()
        initViews()
    }

    private fun initViews()
    {
        binding.run{
            recyclerHomeIngredients.run{
                adapter = ingredientsAdapter
                layoutManager = LinearLayoutManager(context)
            }
            activity?.let {
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
}