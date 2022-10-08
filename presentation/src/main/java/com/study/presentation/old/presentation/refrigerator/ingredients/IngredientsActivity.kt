package com.study.presentation.old.presentation.refrigerator.ingredients

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isGone
import com.study.presentation.R
import com.study.presentation.old.presentation.base.BaseActivity
import com.study.presentation.databinding.ActivityIngredientsBinding
import com.study.presentation.old.model.ingredient.IngredientItem
import com.study.presentation.old.presentation.refrigerator.RefrigeratorFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.title_bar.*
import org.joda.time.Days
import org.joda.time.LocalDate

@AndroidEntryPoint
class IngredientsActivity : BaseActivity<ActivityIngredientsBinding, IngredientsVieModel>(), AdapterView.OnItemSelectedListener, View.OnClickListener {

    companion object {
        const val TYPE = "INGREDIENTS_TYPE"
        const val WRITE = "WRITE"
        const val MODIFY= "MODIFY"
    }

    override val layoutId: Int
        get() = R.layout.activity_ingredients

    private var ingredientsID: Int? = null
    private var shelfLifeDay: Int? = null
    private var type: String? = null

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

    override fun onClick(v: View?) {
        with(binding) {
            when (v?.id) {
                R.id.calendarButton -> {
                    DatePickerFragment().show(supportFragmentManager, "datePicker")
                }

                R.id.saveButton -> {
                    try {
                        val name = ingredientsName.text.toString()
                        val description = ingredientsDescription.text.toString()
                        val quantity = etIngredientsAmount.text.toString().toInt()
                        val unit = amountSpinner.selectedItem.toString()

                        if (isCorrectIngredientData(name, quantity)) return

                        val ingredientItem = IngredientItem(
                            name = name,
                            description = description,
                            quantity = quantity,
                            unit = unit,
                            shelf_life = shelfLifeDay ?: 0
                        )
                        when (type) {
                            com.study.presentation.old.presentation.refrigerator.ingredients.IngredientsActivity.WRITE -> {
                                viewModel.writeTodo(ingredientItem)
                            }
                            com.study.presentation.old.presentation.refrigerator.ingredients.IngredientsActivity.MODIFY -> {
                                viewModel.modifyTodo(ingredientItem)
                            }
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@IngredientsActivity, getString(R.string.error), Toast.LENGTH_SHORT).show()
                        return
                    }
                }

                R.id.imgbtn_back -> {
                    onBackPressed()
                }
            }
        }
    }


    private fun initData() {
        ingredientsID = intent.getIntExtra(RefrigeratorFragment.REFRIGERATOR_EXTRA_ID, -1)
        ingredientsID?.let { viewModel.setId(it) }
        type = intent.getStringExtra(TYPE)
        viewModel.fetchData()
    }

    private fun initViews() = with(binding) {
        calendarTextView.isGone = true
        titleBar.txtHomeTitle.text = "식재료"
        calendarButton.setOnClickListener(this@IngredientsActivity)
        saveButton.setOnClickListener(this@IngredientsActivity)
        imgbtn_back.setOnClickListener(this@IngredientsActivity)
    }

    private fun isCorrectIngredientData(name: String, quantity: Int): Boolean {
        if (isNameCorrect(name)) {
            Toast.makeText(this@IngredientsActivity, getString(R.string.name_error), Toast.LENGTH_SHORT).show()
            return true
        }

        if (isQuantityCorrect(quantity)) {
            Toast.makeText(this@IngredientsActivity, getString(R.string.quantity_error), Toast.LENGTH_SHORT).show()
            return true
        }

        if (shelfLifeDay == null) {
            Toast.makeText(this@IngredientsActivity, getString(R.string.day_error), Toast.LENGTH_SHORT).show()
            return true
        } else if (shelfLifeDay!! < 0) {
            Toast.makeText(this@IngredientsActivity, getString(R.string.shelflife_error), Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    private fun isQuantityCorrect(quantity: Int): Boolean {
        return quantity == 0
    }

    private fun isNameCorrect(name: String): Boolean {
        return name.isEmpty()
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
            text = LocalDate(year, month + 1, day).toString("yyyy-MM-dd")
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
            calendarTextView.text = ingredients?.shelf_life?.let { LocalDate.now().plusDays(it).toString("yyyy-MM-dd") }
            shelfLifeDay = ingredients?.shelf_life
            calendarTextView.isGone = false
        }
    }

    private fun modifyHandler() {
        Toast.makeText(this, getString(R.string.modify_success), Toast.LENGTH_SHORT).show()
        setResult(RESULT_OK)
        finish()
    }

    private fun writeHandler() {
        Toast.makeText(this, getString(R.string.save_success), Toast.LENGTH_SHORT).show()
        setResult(RESULT_OK)
        finish()
    }


}