package com.yeodam.yeodam2019.utils

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.EditText


class LinedEditText(context: Context?, attrs: AttributeSet?) :
    EditText(context, attrs) {

    private val mRect: Rect = Rect()
    private val mPaint: Paint = Paint()

    override fun onDraw(canvas: Canvas) { //int count = getLineCount();

        val height = height
        val line_height = lineHeight
        var count = height / line_height
        if (lineCount > count) count = lineCount //for long text with scrolling
        val r: Rect = mRect
        val paint: Paint = mPaint
        var baseline = getLineBounds(0, r) //first line

        for (i in 0 until count) {
            canvas.drawLine(
                r.left.toFloat(), (baseline + 1).toFloat(),
                r.right.toFloat(), (baseline + 1).toFloat(), paint
            )
            baseline += lineHeight //next line
        }

        super.onDraw(canvas)
    }

    // we need this constructor for LayoutInflater
    init {
        mPaint.style = Paint.Style.FILL_AND_STROKE
        mPaint.color = R.color.darker_gray //SET YOUR OWN COLOR HERE
    }
}