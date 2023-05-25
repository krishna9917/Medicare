package com.application.comeato.Activity.SignUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.application.comeato.Activity.Login.LoginActivity
import com.application.comeato.Dialog.BottomSheet.OtpVerification.OTPVerification
import com.application.comeato.Interfaces.DialogClickListener
import com.application.comeato.LocalMemory.MyLocalMemory
import com.application.comeato.R
import com.application.comeato.Utilities.UtilsFunction
import com.application.comeato.Utilities.UtilsFunction.Companion.setPasswordToggleVisibility
import com.application.comeato.databinding.ActivitySignUpBinding
import com.comeato.Utilities.MyApp

class SignUpActivity : AppCompatActivity(), View.OnClickListener, DialogClickListener {
    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    private val repository by lazy {
        SignUpRepository(MyApp.MySingleton.getApiInterface(), this)
    }
    private val viewModel by lazy {
        ViewModelProvider(this, SignUpViewModelFactory(repository))[SignUpViewModel::class.java]
    }

    private var isPassVisible: Boolean = false
    private var isConfirmPassVisible: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Initializer()
    }

    private fun Initializer() {
        binding.txtLogin.setOnClickListener(this)
        binding.imgPasswordToggle.setOnClickListener(this)
        binding.imgConfirmPassToggle.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
        viewModel.registerResponse.observe(this) {
            binding.isProgress = false
            if (it.status) {
                OTPVerification(this, 1, binding.edtNumber.text.toString()).show(
                    supportFragmentManager,
                    "OTP_verify"
                )
            } else {
                UtilsFunction.showError(this, it.message)
            }
        }



    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.txtLogin -> finish()
            R.id.imgPasswordToggle -> isPassVisible = setPasswordToggleVisibility(
                isPassVisible,
                binding.imgPasswordToggle,
                binding.edtPassword
            )

            R.id.imgConfirmPassToggle -> isConfirmPassVisible = setPasswordToggleVisibility(
                isConfirmPassVisible,
                binding.imgConfirmPassToggle,
                binding.edtConfirmPassword
            )

            R.id.btnRegister -> {
                if (fieldEmptyValidator(
                        binding.edtName,
                        getString(R.string.required_name)
                    ) && emailValidator(binding.edtEmail)
                    && LoginActivity.mobileValidator(binding.edtNumber) && LoginActivity.passwordValidator(
                        binding.edtPassword
                    )
                    && confirmPassword(
                        binding.edtConfirmPassword,
                        binding.edtPassword.text.toString()
                    )
                ) {
                    binding.isProgress = true
                    repository.userRegister(
                        binding.edtName.text.toString(),
                        binding.edtEmail.text.toString(),
                        binding.edtNumber.text.toString(),
                        binding.edtPassword.text.toString(),
                        binding.edtConfirmPassword.text.toString()
                    )
                } else {
                    UtilsFunction.vibration(this)
                }
            }
        }
    }

    companion object {
        fun fieldEmptyValidator(editText: EditText, message: String): Boolean {
            var isValid = true
            if (editText.text.toString().equals("")) {
                editText.error = message
                isValid = false
            }
            return isValid
        }


        fun emailValidator(editText: EditText): Boolean {
            var isValid = true
            if (!fieldEmptyValidator(
                    editText,
                    MyApp.MySingleton.getAppContext().getString(R.string.email_required)
                )
            ) {
                isValid = false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(editText.text.toString()).matches()) {
                editText.error =
                    MyApp.MySingleton.getAppContext().getString(R.string.invalid_email_address)
                isValid = false
            }
            if (!isValid) UtilsFunction.vibration(MyApp.MySingleton.getAppContext())
            return isValid
        }

        fun confirmPassword(editText: EditText, password: String): Boolean {
            var isValid = true
            if (!editText.text.toString().equals(password)) {
                editText.error =
                    MyApp.MySingleton.getAppContext().getString(R.string.password_mismatch)
                isValid = false
            }
            if (!isValid) UtilsFunction.vibration(MyApp.MySingleton.getAppContext())
            return isValid
        }

    }


    override fun onClick(clickCode: Int, data: Any?) {
        if (clickCode == 200){
            finish()
            onBackPressedDispatcher.onBackPressed()
        }
    }


}