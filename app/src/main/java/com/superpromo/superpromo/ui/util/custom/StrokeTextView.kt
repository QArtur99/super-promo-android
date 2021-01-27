package com.superpromo.superpromo.ui.util.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.superpromo.superpromo.R

class StrokeTextView(
    context: Context,
    attrs: AttributeSet
) : AppCompatTextView(context, attrs) {
    public override fun onDraw(canvas: Canvas) {
        val textColor = textColors
        val paint = this.paint
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeMiter = 5f
        this.setTextColor(ContextCompat.getColor(context, R.color.black))
        paint.strokeWidth = 10f
        super.onDraw(canvas)
        paint.style = Paint.Style.FILL
        setTextColor(textColor)
        super.onDraw(canvas)
    }
}