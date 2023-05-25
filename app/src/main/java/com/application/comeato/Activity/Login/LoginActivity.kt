package com.application.comeato.Activity.Login
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.application.comeato.Activity.SignUp.SignUpActivity
import com.application.comeato.BottomSheet.OTPVerification
import com.application.comeato.R
import com.application.comeato.Utilities.UtilsFunction
import com.application.comeato.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private var isPasswordVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Initializer()
    }

    private fun Initializer() {
        binding.txtCreate.setOnClickListener(this)
        binding.imgPasswordToggle.setOnClickListener(this)
        binding.txtOtpSignIn.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.txtCreate -> startActivity(Intent(this, SignUpActivity::class.java))
            R.id.imgPasswordToggle -> isPasswordVisible = UtilsFunction.setPasswordToggleVisibility(isPasswordVisible, binding.imgPasswordToggle, binding.edtPassword)
           R.id.txtOtpSignIn-> OTPVerification().show(supportFragmentManager,"OtpSignIn")
        }
    }
}