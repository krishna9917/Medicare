package com.application.comeato.Activity.SignUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.application.comeato.R
import com.application.comeato.Utilities.UtilsFunction
import com.application.comeato.Utilities.UtilsFunction.Companion.setPasswordToggleVisibility
import com.application.comeato.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity(), View.OnClickListener
{
    private val binding by lazy{
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private var isPassVisible:Boolean=false
    private var isConfirmPassVisible:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Initializer()
    }

    private fun Initializer()
    {
        binding.txtLogin.setOnClickListener(this)
        binding.imgPasswordToggle.setOnClickListener(this)
        binding.imgConfirmPassToggle.setOnClickListener(this)


    }

    override fun onClick(p0: View?)
    {
        when(p0?.id)
        {
            R.id.txtLogin->finish()
            R.id.imgPasswordToggle-> isPassVisible=setPasswordToggleVisibility(isPassVisible,binding.imgPasswordToggle,binding.edtPassword)
            R.id.imgConfirmPassToggle->isConfirmPassVisible=setPasswordToggleVisibility(isConfirmPassVisible,binding.imgConfirmPassToggle,binding.edtConfirmPassword)
        }

    }



}