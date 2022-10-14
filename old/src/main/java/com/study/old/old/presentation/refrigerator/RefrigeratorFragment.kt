package com.study.old.old.presentation.refrigerator

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.study.old.R
import com.study.old.old.presentation.base.BaseFragment
import com.study.old.databinding.FragmentRefrigeratorBinding
import com.study.old.old.presentation.refrigerator.ingredients.IngredientsActivity
import com.study.old.old.presentation.view.ItemDecorate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RefrigeratorFragment : BaseFragment<FragmentRefrigeratorBinding, RefrigeratorViewModel>() {

    companion object {
        const val TAG = "REFRIGERATOR_FRAGMENT"
        fun newInstance(): RefrigeratorFragment {
            val args = Bundle()
            val fragment = RefrigeratorFragment()
            fragment.arguments = args
            return fragment
        }

        const val REFRIGERATOR_EXTRA_ID = "REFRIGERATOR_EXTRA_ID"
    }

    override val viewModel: RefrigeratorViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.fragment_refrigerator

    private val refrigeratorRecyclerViewAdapter by lazy {
        RefrigeratorRecyclerViewAdapter(itemOnClicked = { ingredients ->
            val intent = context?.let { Intent(it, IngredientsActivity::class.java) }
            intent?.putExtra(REFRIGERATOR_EXTRA_ID, ingredients.id)
            intent?.putExtra(IngredientsActivity.TYPE, IngredientsActivity.MODIFY)
            startActivity(intent) //intent extra 넘길 예정
        }, deleteOnClicked = {
            viewModel.delete(it)
        })
    }

    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                viewModel.fetchData()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initRecyclerView()
        observeData()
        viewModel.fetchData()
    }

    //베이스로 뺄 예정
    override fun observeData(){
        viewModel.refrigeratorLiveData.observe(viewLifecycleOwner){ refrigeratorState ->
            when (refrigeratorState) {
                is RefrigeratorState.UnInitialize -> {

                }
                is RefrigeratorState.Loading -> {
                    //프로그래스 바 보임 처리
                }
                is RefrigeratorState.Success -> {
                    refrigeratorRecyclerViewAdapter.submitList(refrigeratorState.recipeList)
                }
                is RefrigeratorState.Delete -> {
                    Toast.makeText(requireContext(), getString(R.string.delete_success), Toast.LENGTH_SHORT).show()
                    viewModel.fetchData()
                }
                is RefrigeratorState.Error -> {
                    Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initViews() {
        binding.titleBar.txtHomeTitle.text = getString(R.string.my_refrigerator)
        binding.addFAButton.setOnClickListener {
            context?.let {
                val intent = context?.let { Intent(it, IngredientsActivity::class.java) }
                intent?.putExtra(IngredientsActivity.TYPE, IngredientsActivity.WRITE)
                getResult.launch(intent)
            }
        }
    }

    private fun initRecyclerView() {
        binding.refrigeratorRecyclerView.adapter = refrigeratorRecyclerViewAdapter
        binding.refrigeratorRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.refrigeratorRecyclerView.addItemDecoration(ItemDecorate())
    }


}