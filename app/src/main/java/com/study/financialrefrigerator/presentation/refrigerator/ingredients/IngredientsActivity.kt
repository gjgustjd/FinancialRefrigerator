package com.study.financialrefrigerator.presentation.refrigerator.ingredients

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isGone
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.base.BaseActivity
import com.study.financialrefrigerator.databinding.ActivityIngredientsBinding
import com.study.financialrefrigerator.model.ingredient.IngredientItem
import com.study.financialrefrigerator.presentation.refrigerator.RefrigeratorFragment
import dagger.hilt.android.AndroidEntryPoint
import org.joda.time.Days
import org.joda.time.LocalDate

@AndroidEntryPoint
class IngredientsActivity : BaseActivity<ActivityIngredientsBinding, IngredientsVieModel>(), AdapterView.OnItemSelectedListener {

    override val layoutId: Int
        get() = R.layout.activity_ingredients

    private var ingredientsID: Int? = null

    private var shelfLifeDay: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        initViews()
        initSpinner()
    }

    override val viewModel: IngredientsVieModel by viewModels()

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun observeData() {
        viewModel.ingredientsLiveData.observe(this) {
            when (it) {
                is IngredientsState.UnInitialize -> {
                    initViews()
                }
                is IngredientsState.Loading -> {
                    showProgressBar()
                }
                is IngredientsState.Success -> {
                    hideProgressBar()
                    successHandler(it.ingredientItem)
                }

                IngredientsState.Modify -> {
                    modifyHandler()
                }

                IngredientsState.Write -> {
                    writeHandler()
                }

                is IngredientsState.Error -> {

                }

            }
        }
    }

    private fun initData() {
        ingredientsID = intent.getIntExtra(RefrigeratorFragment.REFRIGERATOR_EXTRA_ID, -1)
        ingredientsID?.let { viewModel.setId(it) }
        viewModel.fetchData()
    }

    private fun initViews() = with(binding) {
        calendarTextView.isGone = true
        titleBar.txtHomeTitle.text = "식재료"
        ingredientsName.setOnClickListener {
            Log.d(",", ",")
        }

        calendarButton.setOnClickListener {
            DatePickerFragment().show(supportFragmentManager, "datePicker")
        }

        saveButton.setOnClickListener {
            try {
                val name = ingredientsName.text.toString()
                val description = ingredientsDescription.text.toString()
                val quantity = etIngredientsAmount.text.toString().toInt()
                val unit = amountSpinner.selectedItem.toString()

                if (name.isEmpty() || quantity == 0 || unit.isEmpty() || shelfLifeDay == null) {
                    Toast.makeText(this@IngredientsActivity, "정보를 정확히 입력해주세요", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else if (shelfLifeDay!! < 0) {
                    Toast.makeText(this@IngredientsActivity, "이미 유통기한이 지난 식재료입니다.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val ingredientItem = IngredientItem(
                    name = name,
                    description = description,
                    quantity = quantity,
                    unit = unit,
                    shelf_life = shelfLifeDay ?: 0

                )
                viewModel.writeTodo(ingredientItem)
            } catch (e: Exception) {
                Toast.makeText(this@IngredientsActivity, "에러가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(this, R.array.spinner_array, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.amountSpinner.adapter = adapter
        }
    }

    fun setCalendarDate(year: Int, month: Int, day: Int) {
        shelfLifeDay = Days.daysBetween(LocalDate.now(), LocalDate(year, month + 1, day)).days + 1
        binding.calendarTextView.run {
            text = LocalDate(year, month, day).toString("yyyy-MM-dd")
            isGone = false
        }
    }


    private fun successHandler(ingredients: IngredientItem?) {
        with(binding) {
            ingredientsName.setText(ingredients?.name ?: "")
            ingredientsDescription.setText(ingredients?.description ?: "")
            if (ingredients?.quantity == null) {
                etIngredientsAmount.setText("")
            } else {
                etIngredientsAmount.setText(ingredients.quantity.toString())
            }
        }
    }

    private fun modifyHandler() {

    }

    private fun writeHandler() {
        Toast.makeText(this, "저장에 성공하였습니다.", Toast.LENGTH_SHORT).show()
        setResult(RESULT_OK)
        finish()
    }

}