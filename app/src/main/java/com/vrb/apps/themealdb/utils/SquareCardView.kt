package com.vrb.apps.themealdb.utils

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.card.MaterialCardView

class SquareCardView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : MaterialCardView(context, attrs, defStyleAttr) {

    constructor(context: Context?) : this(context, null, 0)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

}