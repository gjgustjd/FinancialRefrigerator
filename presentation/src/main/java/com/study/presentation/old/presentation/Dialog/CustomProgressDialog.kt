package com.study.presentation.old.presentation.Dialog

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.study.presentation.R

class CustomProgressDialog: DialogFragment() {

    companion object {
        const val EXTRA_LAYOUT_RES = "EXTRA_LAYOUT_RES"
        const val EXTRA_IS_CANCELABLE = "EXTRA_IS_CANCELABLE"

        fun getInstance(): CustomProgressDialog = CustomProgressDialog()
    }

    @LayoutRes private var layoutRes: Int = R.layout.dialog_progressbar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        this.isCancelable = arguments?.getBoolean(EXTRA_IS_CANCELABLE) ?: false
        this.layoutRes = arguments?.getInt(EXTRA_LAYOUT_RES) ?: R.layout.dialog_progressbar
        return super.onCreateDialog(savedInstanceState)
    }
}