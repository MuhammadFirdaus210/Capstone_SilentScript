package com.example.silentscript.customeview.getstarted

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.silentscript.R

class Register : AppCompatButton {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var txtColor: Int = 0
    private var enabledBackground: Drawable

    init {
        txtColor = if(id == R.id.login) {
            ContextCompat.getColor(context, R.color.white)
            } else{
            ContextCompat.getColor(context, R.color.blue_4)
        }
        enabledBackground = if(id == R.id.login) {
            ContextCompat.getDrawable(context, R.drawable.backgroud_blue) as Drawable
            }else {
            ContextCompat.getDrawable(context, R.drawable.button_backgroud) as Drawable
            }
        }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
            background = enabledBackground
            setTextColor(txtColor)
            textSize = 12f
            gravity = Gravity.CENTER

        text = if (id == R.id.login) {
            context.getString(R.string.login)
            } else {
            ContextCompat.getString(context, R.string.start)
        }
    }
}
