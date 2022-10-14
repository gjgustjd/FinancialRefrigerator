package com.study.old.old.common

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import com.study.old.R
import kotlinx.android.synthetic.main.dialog_general.view.*

class DialogUtil(val context: Context) {
    fun showTwoBtn(
        title: String, content: String,
        isCancel: Boolean = true,
        confirmBtnText: String = context.getString(R.string.text_confirm),
        cancelBtnText: String = context.getString(R.string.text_cancel),
        confirmClickListener: ()->Unit={},
        cancelClickListener: () -> Unit={},
    ) {
        val builder = AlertDialog.Builder(ContextThemeWrapper(context,null))
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_general, null)
        builder.setView(dialogView)

        val dialog = builder.create().apply {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(isCancel)
        }
        with(dialogView.dialogLayout)
        {
            tvTitle.text = title
            tvContent.text = content
            tvConfirm.text = confirmBtnText

            tvCancel.text = cancelBtnText

            tvConfirm.setOnClickListener {
                confirmClickListener()
                dialog.dismiss()
            }
            tvCancel.setOnClickListener {
                cancelClickListener()
                dialog.dismiss()
            }
            dialog.show()
        }

    }
}