package com.besirkaraoglu.rickandmorty.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

const val DEFAULT_MARGIN = 10

class GridItemDecoration(private val margin: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.right = margin
        outRect.left = margin
        outRect.bottom = margin

        if (parent.getChildAdapterPosition(view) < 2){
            outRect.top = margin
        }
    }
}