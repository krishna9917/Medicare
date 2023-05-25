package com.application.comeato.BottomSheet
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.application.comeato.R
import com.application.comeato.databinding.LayoutOtpVerifcationBinding
import com.comeato.Activity.Home.HomeActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*
import java.util.concurrent.TimeUnit

class OTPVerification : BottomSheetDialogFragment(),View.OnClickListener
{
    private val binding by lazy{
        LayoutOtpVerifcationBinding.inflate(layoutInflater)
    }
    lateinit var countDownTimer:CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME,0);
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
    }

    override fun onClick(p0: View?)
    {
        when(p0?.id)
        {
            R.id.imgClose-> dismiss()
            R.id.btnContinue-> if(binding.clMobileInput.visibility==View.VISIBLE)
            {
                binding.clOTPInput.visibility=View.VISIBLE
                binding.clMobileInput.visibility=View.GONE
                binding.btnContinue.text = getString(R.string.confirm)
                binding.btnContinue.setCompoundDrawables(null,null,null,null)
                startTimer()
            }else if(binding.clOTPInput.visibility==View.VISIBLE)
            {
                countDownTimer.cancel()
                startActivity(Intent(context,HomeActivity::class.java))
                dismiss()
            }
        }
    }

    private fun startTimer()
    {
        binding.imgSandTimer.visibility=View.VISIBLE
         countDownTimer=object: CountDownTimer(120000, 1000) {
            override fun onTick(millisUntilFinished: Long)
            {

               val time= String.format("%02d:%02d ", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))

                binding.txtCountDown.text=time
            }
            override fun onFinish()
            {
                binding.imgSandTimer.visibility=View.GONE
                binding.txtCountDown.text = getString(R.string.resend_otp)
            }
        }.start()

    }
}