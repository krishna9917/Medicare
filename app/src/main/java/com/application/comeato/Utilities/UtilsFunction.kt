package com.application.comeato.Utilities

import android.content.Context
import android.text.InputType
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.application.comeato.R
import com.comeato.Utilities.MyApp

class UtilsFunction {

    companion object {

        fun setAnimation(context: Context?, viewToAnimate: View, animationId: Int)
        {
            val animation = AnimationUtils.loadAnimation(context, animationId)
            viewToAnimate.startAnimation(animation)
        }

        fun setPasswordToggleVisibility(password: Boolean, imgPasswordToggle: ImageView, editField: EditText):Boolean
        {
            var passwordVisible=password
            if(!passwordVisible)
            {
                passwordVisible=true
                imgPasswordToggle.setImageDrawable(ContextCompat.getDrawable(MyApp.MySingleton.getAppContext(),R.drawable.ic_hide_eye))
                editField.inputType = InputType.TYPE_CLASS_TEXT
                editField.setSelection( editField.text.toString().length)

            }else
            {
                passwordVisible=false
                imgPasswordToggle.setImageDrawable( ContextCompat.getDrawable(MyApp.MySingleton.getAppContext(),R.drawable.ic_eye))
                editField.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                editField.setSelection(editField.text.toString().length)

            }
            return passwordVisible
        }
    }

}