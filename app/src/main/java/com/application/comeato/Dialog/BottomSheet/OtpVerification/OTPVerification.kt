package com.application.comeato.Dialog.BottomSheet.OtpVerification
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.application.comeato.Activity.Login.LoginActivity
import com.application.comeato.Activity.WishList.WishListRepository
import com.application.comeato.Activity.WishList.WishListViewModel
import com.application.comeato.Activity.WishList.WishListViewModelFactory
import com.application.comeato.Interfaces.DialogClickListener
import com.application.comeato.LocalMemory.MyLocalMemory
import com.application.comeato.R
import com.application.comeato.RoomDb.MyDatabase
import com.application.comeato.Utilities.UtilsFunction
import com.application.comeato.databinding.LayoutOtpVerifcationBinding
import com.application.comeato.models.OtpDataWithLocalWishlist
import com.application.comeato.models.WishListData
import com.comeato.Utilities.MyApp
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import `in`.aabhasjindal.otptextview.OtpTextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


//layout  1,0 use for SignIn and  2 use for forgot password
class OTPVerification(val clickListener:DialogClickListener, val layoutType:Int=0,var mobileNumber:String="") : BottomSheetDialogFragment(),View.OnClickListener
{

    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.READ_SMS
    )


    private val roomDatabase by lazy {
        MyDatabase.getInstance(requireContext()).getRoomDao()
    }

    private val repository by lazy {
        OtpVerifyRepository(MyApp.MySingleton.getApiInterface(),requireContext())
    }
    private val viewModel by lazy {
        ViewModelProvider(this, OtpVerifyViewModelFactory(repository))[OtpVerifyViewModel::class.java]
    }

    private val wishListRepository by lazy {
        WishListRepository(MyApp.MySingleton.getApiInterface(),requireContext(),roomDatabase)
    }

    private val wishListViewModel by lazy {
        ViewModelProvider(this, WishListViewModelFactory(wishListRepository))[WishListViewModel::class.java]
    }


    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val allPermissionsGranted = permissions.all { it.value }
    }

    private val binding by lazy{
        LayoutOtpVerifcationBinding.inflate(layoutInflater)
    }
    lateinit var countDownTimer:CountDownTimer

    private lateinit var localWishList :List<WishListData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL,R.style.BottomSheetDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        Initializer()
        return binding.root
    }

    private fun Initializer()
    {
        binding.imgClose.setOnClickListener(this)
        binding.btnContinue.setOnClickListener(this)
        binding.llResendOtp.setOnClickListener(this)

        getData()

        if(layoutType==1) otpSend()

    }


    private fun getData()
    {
        viewModel.verificationResponse.observe(this) {
            binding.isLoading=false
            if(it.status)
            {
                countDownTimer.cancel()
                dismiss()
                if(layoutType==1 || layoutType==0)
                {
                    MyLocalMemory.putObject(MyApp.MySingleton.USER_DETAILS, it?.user)
                    MyLocalMemory.putBoolean(MyApp.MySingleton.IS_LOGIN, true)
                    MyLocalMemory.putString(MyApp.MySingleton.USER_TOKEN, it?.access_token!!)
                    lifecycleScope.launch(Dispatchers.IO) {
                        roomDatabase.deleteWishLists()
                    }
                    clickListener.onClick(200)
                }else if(layoutType==2)
                {
                    clickListener.onClick(300,mobileNumber)
                }
            }else
            {
                UtilsFunction.showError(requireContext(),it.message)
            }
        }

        viewModel.sendOtpResponse.observe(this){
            binding.isLoading=false
            if(it.status)
            {
                UtilsFunction.showToast(requireContext(),it.message)
                otpSend()
            }else
            {
                UtilsFunction.showError(requireContext(),it.message)
            }
        }

        //it handle wishlist data
        wishListViewModel.getWishList.observe(this){
            localWishList = it
        }
    }



    override fun onClick(p0: View?)
    {
        if(layoutType==0 || layoutType==2) mobileNumber= binding.edtNumber.text.toString()
        when(p0?.id)
        {
            R.id.imgClose-> dismiss()
            R.id.btnContinue-> {

                if(binding.clMobileInput.visibility==View.VISIBLE)
                {
                    if(LoginActivity.mobileValidator(binding.edtNumber))
                    {
                        viewModel.repository.otpSend(binding.edtNumber.text.toString())
                        binding.isLoading=true
                    }
                } else if(binding.clOTPInput.visibility==View.VISIBLE)
                {
                   if(otpValidator(binding.otpView))
                   {
                       if(layoutType==1 || layoutType==0)
                       {
                           viewModel.repository.verifyMobileThenLogin(OtpDataWithLocalWishlist(mobileNumber,binding.otpView.otp!!,localWishList))

                       }else
                       {
                           viewModel.repository.verifyMobile(mobileNumber,binding.otpView.otp!!)
                       }
                       binding.isLoading=true
                   }
                }
            }
          R.id.llResendOtp ->  viewModel.repository.otpSend(mobileNumber)
        }
    }




    private fun otpSend()
    {
        if(!(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED))
        {
            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
        }
        binding.clOTPInput.visibility=View.VISIBLE
        binding.clMobileInput.visibility=View.GONE
        binding.btnContinue.text = getString(R.string.confirm)
        binding.btnContinue.setCompoundDrawables(null,null,null,null)
        startTimer()
    }





    private fun startTimer()
    {
         countDownTimer=object: CountDownTimer(120000, 1000) {
            override fun onTick(millisUntilFinished: Long)
            {
               val time= String.format("%02d:%02d ", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
                binding.countDown=time
            }
            override fun onFinish()
            {
                binding.countDown = MyApp.MySingleton.getAppContext().getString(R.string.resend_otp)
            }
        }.start()
    }




    companion object
    {
        fun otpValidator(otpView: OtpTextView): Boolean
        {
            var isValid=true
            if(otpView.otp.equals(""))
            {
                UtilsFunction.showError(otpView.context,MyApp.MySingleton.getAppContext().getString(R.string.please_enter_otp))
                isValid=false
            }else if(otpView.otp!!.length<4)
            {
                UtilsFunction.showError(otpView.context,MyApp.MySingleton.getAppContext().getString(R.string.enter_valid_otp_code))
                isValid=false
            }
            if(!isValid) otpView.showError()
            return isValid
        }

    }

}