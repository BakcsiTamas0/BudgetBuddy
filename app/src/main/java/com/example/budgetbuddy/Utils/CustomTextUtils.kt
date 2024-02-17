package com.example.budgetbuddy.Utils

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.text.TextPaint
import android.widget.TextView

class CustomTextUtils {
    fun applyLinearGradient(textView: TextView) {
        textView.text = textView.text.toString().uppercase()

        val paint: TextPaint = textView.paint
        val width: Float = paint.measureText(textView.text.toString())

        val textShader: Shader = LinearGradient(
            0f, 0f, width + 10, textView.textSize,
            intArrayOf(
                Color.parseColor("#ff1b6b"),
                Color.parseColor("#45caff")
            ), null, Shader.TileMode.CLAMP
        )

        val underlineShader: Shader = LinearGradient(
            0f, 0f, width + 10, textView.textSize,
            intArrayOf(
                Color.parseColor("#ff1b6b"),
                Color.parseColor("#45caff")
            ), null, Shader.TileMode.CLAMP
        )

        paint.shader = textShader

        val underlinePaint: Paint = textView.paint
        underlinePaint.shader = underlineShader
        underlinePaint.style = Paint.Style.FILL_AND_STROKE
        underlinePaint.strokeWidth = 1f
        textView.paint.isUnderlineText = true
    }

}