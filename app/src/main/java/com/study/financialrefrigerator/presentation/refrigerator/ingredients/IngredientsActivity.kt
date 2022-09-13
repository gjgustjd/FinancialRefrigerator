package com.study.financialrefrigerator.presentation.refrigerator.ingredients

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import com.study.financialrefrigerator.Companion.INGREDIENTS_AMOUNT
import com.study.financialrefrigerator.Companion.INGREDIENTS_DAY
import com.study.financialrefrigerator.Companion.INGREDIENTS_NAME
import com.study.financialrefrigerator.R
import com.study.financialrefrigerator.base.BaseActivity
import com.study.financialrefrigerator.databinding.ActivityIngredientsBinding

class IngredientsActivity : BaseActivity<ActivityIngredientsBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_ingredients

    private lateinit var ingredientsName:String
    private lateinit var ingredientsAmount:String
    private var ingredientsDay:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        initViews()
    }

    private fun initData() {
        ingredientsName = intent.getStringExtra(INGREDIENTS_NAME).toString()
        ingredientsAmount = intent.getStringExtra(INGREDIENTS_AMOUNT).toString()
        ingredientsDay = intent.getIntExtra(INGREDIENTS_DAY, 0)
    }

    private fun initViews() {
        binding.ingredientsName.setOnClickListener {
            Log.d(",",",")
        }
        binding.calendarButton.setOnClickListener {
            DatePickerFragment().show(supportFragmentManager,"datePicker")
        }
    }

    fun setCalendarDate(year:Int, month:Int, day:Int){
        Toast.makeText(this,"$year, $month, $day", Toast.LENGTH_SHORT).show()
    }


}