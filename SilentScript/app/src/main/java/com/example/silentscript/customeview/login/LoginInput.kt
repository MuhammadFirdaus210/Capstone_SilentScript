package com.example.silentscript.customeview.login

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.silentscript.R

class LoginInput : AppCompatEditText {

    private lateinit var username: Drawable
    private lateinit var password: Drawable
    private lateinit var email: Drawable
    private lateinit var confirmation: Drawable
    private lateinit var search: Drawable

    private var passwordEditText: AppCompatEditText? = null
    private var confirmationEditText: AppCompatEditText? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {

        when (id) {
            R.id.username -> {
                username = ContextCompat.getDrawable(context, R.drawable.baseline_person) as Drawable
                inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                compoundDrawablePadding = 16

                setHint(R.string.username)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setAutofillHints(AppCompatEditText.AUTOFILL_HINT_EMAIL_ADDRESS)
                }
                setDrawable(username)
            }
            R.id.email -> {
                email = ContextCompat.getDrawable(context, R.drawable.baseline_email) as Drawable
                inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                compoundDrawablePadding = 16

                setHint(R.string.email)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setAutofillHints(AUTOFILL_HINT_EMAIL_ADDRESS)
                }
                setDrawable(email)
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun afterTextChanged(p0: Editable?) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        if (!s.isNullOrEmpty() && s.length < 8)
                            error = context.getString(R.string.error_email)
                    }
                })
            }
            R.id.confirmation -> {
                confirmation = ContextCompat.getDrawable(context, R.drawable.baseline_lock) as Drawable
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                compoundDrawablePadding = 16

                setHint(R.string.confirmation)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setAutofillHints(AUTOFILL_HINT_PASSWORD)
                }
                setDrawable(confirmation)
                confirmationEditText = this

                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun afterTextChanged(p0: Editable?) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        if (!s.isNullOrEmpty() && s.length < 8)
                            error = context.getString(R.string.error_password)
                        checkPasswordMatch()
                    }
                })
            }
            R.id.password -> {
                password = ContextCompat.getDrawable(context, R.drawable.baseline_lock) as Drawable
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                compoundDrawablePadding = 16

                setHint(R.string.password)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setAutofillHints(AUTOFILL_HINT_PASSWORD)
                }
                setDrawable(password)
                passwordEditText = this

                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun afterTextChanged(p0: Editable?) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        if (!s.isNullOrEmpty() && s.length < 8)
                            error = context.getString(R.string.error_password)
                        checkPasswordMatch()
                    }
                })
            }
            R.id.search -> {
                search = ContextCompat.getDrawable(context, R.drawable.ic_seacrh) as Drawable
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                compoundDrawablePadding = 16

                setHint(R.string.search)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setAutofillHints(AUTOFILL_HINT_PASSWORD)
                }
                setDrawable(search)
                passwordEditText = this
            }
        }
    }

    private fun checkPasswordMatch() {
        val password = passwordEditText?.text.toString()
        val confirmation = confirmationEditText?.text.toString()

        if (passwordEditText != null && confirmationEditText != null) {
            if (password != confirmation) {
                confirmationEditText?.error = context.getString(R.string.error_confirmation)
            } else {
                confirmationEditText?.error = null
            }
        }
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
