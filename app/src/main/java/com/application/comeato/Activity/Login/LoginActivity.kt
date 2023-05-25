package com.application.comeato.Activity.Login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.application.comeato.Activity.ForgotPassword.ForgotPasswordActivity
import com.application.comeato.Activity.SignUp.SignUpActivity
import com.application.comeato.Activity.WishList.WishListRepository
import com.application.comeato.Activity.WishList.WishListViewModel
import com.application.comeato.Activity.WishList.WishListViewModelFactory
import com.application.comeato.Dialog.BottomSheet.OtpVerification.OTPVerification
import com.application.comeato.Interfaces.DialogClickListener
import com.application.comeato.LocalMemory.MyLocalMemory
import com.application.comeato.R
import com.application.comeato.RoomDb.MyDatabase
import com.application.comeato.Utilities.UtilsFunction
import com.application.comeato.databinding.ActivityLoginBinding
import com.application.comeato.models.LoginDataWithLocalWishList
import com.application.comeato.models.WishListData
import com.comeato.Utilities.MyApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class LoginActivity : AppCompatActivity(), View.OnClickListener, DialogClickListener {

    private val roomDatabase by lazy {
        MyDatabase.getInstance(this).getRoomDao()
    }

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val loginRepository by lazy {
        LoginRepository(MyApp.MySingleton.getApiInterface(), this)
    }

    private val loginViewModel by lazy {
        ViewModelProvider(this, LoginViewModelFactory(loginRepository))[LoginViewModel::class.java]
    }


    private val wishListRepository by lazy {
        WishListRepository(MyApp.MySingleton.getApiInterface(), this, roomDatabase)
    }

    private val wishListViewModel by lazy {
        ViewModelProvider(
            this,
            WishListViewModelFactory(wishListRepository)
        )[WishListViewModel::class.java]
    }


    private lateinit var localWishList: List<WishListData>

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
        binding.btnLogin.setOnClickListener(this)
        binding.txtForgotPass.setOnClickListener(this)

        getData()


    }

    private fun getData() {
        //it observe all login response
        loginViewModel.loginResponse.observe(this) {
            binding.isLoading = false
            if (it.status) {
                MyLocalMemory.putObject(MyApp.MySingleton.USER_DETAILS, it?.user)
                MyLocalMemory.putBoolean(MyApp.MySingleton.IS_LOGIN, true)
                MyLocalMemory.putString(MyApp.MySingleton.USER_TOKEN, it?.access_token!!)

                lifecycleScope.launch(Dispatchers.IO) {
                    roomDatabase.deleteWishLists()
                }
                finish()
                onBackPressedDispatcher.onBackPressed()
            } else {
                UtilsFunction.showError(this, it.message)
            }
        }


        //it handle wishlist data
        wishListViewModel.getWishList.observe(this) {
            localWishList = it
        }

    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnLogin -> {
                if (SignUpActivity.emailValidator(binding.edtEmail) && passwordValidator(binding.edtPassword)) {
                    binding.isLoading = true
                    loginRepository.loginByPassword(
                        LoginDataWithLocalWishList(
                            binding.edtEmail.text.toString(),
                            binding.edtPassword.text.toString(),
                            localWishList
                        )
                    )
                } else {
                    UtilsFunction.vibration(this)
                }
            }

            R.id.txtCreate -> startActivity(Intent(this, SignUpActivity::class.java))
            R.id.imgPasswordToggle -> isPasswordVisible = UtilsFunction.setPasswordToggleVisibility(
                isPasswordVisible,
                binding.imgPasswordToggle,
                binding.edtPassword
            )

            R.id.txtOtpSignIn -> OTPVerification(this).show(supportFragmentManager, "OtpSignIn")
            R.id.txtForgotPass -> OTPVerification(this, 2).show(
                supportFragmentManager,
                "ForgotPassword"
            )
        }
    }

    companion object {
        fun mobileValidator(editField: EditText): Boolean {
            var isValid = true
            val mobileNumber = editField.text.toString()
            if (!SignUpActivity.fieldEmptyValidator(
                    editField,
                    MyApp.MySingleton.getAppContext().getString(R.string.please_enter_mobile_number)
                )
            ) {
                isValid = false
            } else if (mobileNumber.length < 10 || !(mobileNumber[0].equals('9') || mobileNumber[0].equals(
                    '8'
                ) || mobileNumber[0].equals('7') || mobileNumber[0].equals('6'))
            ) {
                editField.error =
                    MyApp.MySingleton.getAppContext().getString(R.string.invalid_mobile_number)
                isValid = false
            }
            if (!isValid) UtilsFunction.vibration(MyApp.MySingleton.getAppContext())
            return isValid
        }

        fun passwordValidator(editField: EditText): Boolean {
            var isValid = true
            if (!SignUpActivity.fieldEmptyValidator(
                    editField,
                    MyApp.MySingleton.getAppContext().getString(R.string.enter_password)
                )
            ) {
                isValid = false
            } else if (editField.text.toString().length < 8) {
                editField.error = MyApp.MySingleton.getAppContext()
                    .getString(R.string.invalid_password_min_8_char)
                isValid = false
            }
            if (!isValid) UtilsFunction.vibration(MyApp.MySingleton.getAppContext())
            return isValid
        }

    }


    override fun onResume() {
        super.onResume()
        if (MyLocalMemory.getBoolean(MyApp.MySingleton.IS_LOGIN)){
            finish()
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onClick(clickCode: Int, data: Any?) {
        when (clickCode) {
            200 -> finish()
            300 -> {
                val mobileNumber = data as String
                val forgotIntent = Intent(this, ForgotPasswordActivity::class.java)
                forgotIntent.putExtra(getString(R.string.mobile_number), mobileNumber)
                startActivity(forgotIntent)
            }
        }
    }


}