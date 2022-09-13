package com.study.financialrefrigerator.presentation.refrigerator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.study.financialrefrigerator.Companion.INGREDIENTS_AMOUNT
import com.study.financialrefrigerator.Companion.INGREDIENTS_DAY
import com.study.financialrefrigerator.Companion.INGREDIENTS_NAME
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.base.BaseFragment
import com.study.financialrefrigerator.databinding.FragmentRefrigeratorBinding
import com.study.financialrefrigerator.presentation.refrigerator.ingredients.IngredientsActivity

class RefrigeratorFragment : BaseFragment<FragmentRefrigeratorBinding>() {

    companion object {
        const val TAG = "REFRIGERATOR_FRAGMENT"
        fun newInstance(): RefrigeratorFragment {
            val args = Bundle()
            val fragment = RefrigeratorFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: RefrigeratorViewModel by viewModels()
    private val refrigeratorRecyclerViewAdapter by lazy {
        RefrigeratorRecyclerViewAdapter(itemOnClicked = { ingredients ->
            val intent = activity?.let{Intent(it, IngredientsActivity::class.java)}
            intent?.apply {
                putExtra(INGREDIENTS_NAME,ingredients.name) // 식별값만 intent로 넘겨서 해당 액티비티에서 식별값을 통해 판단.
                startActivity(this)
            }
        }, deleteOnClicked = {

        })
    }

    override val layoutId: Int
        get() = R.layout.fragment_refrigerator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeData()
        viewModel.fetchData()
    }

    private fun initRecyclerView() {
        binding.refrigeratorRecyclerView.adapter = refrigeratorRecyclerViewAdapter
        binding.refrigeratorRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
    }

    //베이스로 뺄 예정
    fun observeData(){
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
                }
                is RefrigeratorState.Error -> {
                    Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}