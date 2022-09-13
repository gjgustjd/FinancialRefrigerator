package com.study.financialrefrigerator.presentation.refrigerator.ingredients

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment: DialogFragment(),DatePickerDialog.OnDateSetListener {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar:Calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return activity?.let {
            DatePickerDialog(it,this,year,month,day)
        } ?: super.onCreateDialog(savedInstanceState)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        try {
            val activityView = activity as IngredientsActivity
            activityView.setCalendarDate(year,month,dayOfMonth)
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }
}