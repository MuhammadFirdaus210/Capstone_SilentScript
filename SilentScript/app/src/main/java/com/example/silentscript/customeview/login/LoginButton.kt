package com.example.silentscript.customeview.login

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.silentscript.R

class LoginButton : AppCompatButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var txtColor: Int = 0
    private var enabledBackground: Drawable
    private lateinit var iconGoogle: Drawable

    init {
        if(id == R.id.play){
            iconGoogle = ContextCompat.getDrawable(context, R.drawable.baseline_play_circle_outline_24) as Drawable
            val startMargin = 30
            val layerDrawable = createDrawableWithMargin(iconGoogle, startMargin, 0, 0, 0)
            setDrawable(layerDrawable)
        } else {
            setDrawable()

        }

        txtColor = if(id == R.id.login) {
            ContextCompat.getColor(context, R.color.white)
        } else if (id == R.id.register_register) {
            ContextCompat.getColor(context, R.color.white)
        }else if (id == R.id.play) {
            ContextCompat.getColor(context, R.color.white)
        }else {
            ContextCompat.getColor(context, R.color.blue_4)
        }
        enabledBackground = if(id == R.id.login) {
            ContextCompat.getDrawable(context, R.drawable.backgroud_blue) as Drawable
        }else if (id == R.id.register_register) {
            ContextCompat.getDrawable(context, R.drawable.backgroud_blue) as Drawable
        } else if (id == R.id.play) {
            ContextCompat.getDrawable(context, R.drawable.backgroud_blue) as Drawable
        }
        else {
            ContextCompat.getDrawable(context, R.drawable.background_button_white) as Drawable
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = enabledBackground
        setTextColor(txtColor)
        textSize = 12f
        gravity = Gravity.CENTER

        when(id){
            R.id.login -> {
                text = context.getString(R.string.login)
            }
            R.id.register_register -> {
                text = context.getString(R.string.register)
            }
        }
    }

    private fun createDrawableWithMargin(
        drawable: Drawable,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ): LayerDrawable {
        val insetDrawable = InsetDrawable(drawable, left, top, right, bottom)
        return LayerDrawable(arrayOf(insetDrawable))
    }

    private fun setDrawable(
        start: Drawable? = null,
        top: Drawable? = null,
        end: Drawable? = null,
        bottom: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom)
    }

}