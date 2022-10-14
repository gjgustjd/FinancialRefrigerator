package com.study.old.old.presentation.view

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ItemDecorate : RecyclerView.ItemDecoration() {

    private fun dpToPx(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        outRect.left = dpToPx(view.context, 10)
        outRect.right = dpToPx(view.context, 10)


        outRect.top = dpToPx(view.context, 10)
        outRect.bottom = dpToPx(view.context, 10)


        val lp: GridLayoutManager.LayoutParams = view.layoutParams as (GridLayoutManager.LayoutParams)

        val spanIndex = lp.spanIndex

        if (spanIndex == 0) {
            outRect.left = dpToPx(view.context, 10)
            outRect.right = dpToPx(view.context, 10)
        } else if (spanIndex == 1) {
            outRect.left = dpToPx(view.context, 10)
            outRect.right = dpToPx(view.context, 10)
        }

    }

}