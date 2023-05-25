package com.application.comeato.Dialog.BottomSheet.OtpVerification

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.comeato.Interfaces.ApiCallListener
import com.application.comeato.models.OtpDataWithLocalWishlist
import com.application.comeato.models.StatusMessage
import com.application.comeato.models.UserDetail
import com.comeato.ApiService.ApiInterface
import com.comeato.Utilities.MyApp
import retrofit2.Response

class OtpVerifyRepository(private val apiInterface: ApiInterface, private val context: Context) : ApiCallListener {

    private val otpVerificationResponse = MutableLiveData<UserDetail>()

    private val otpSendResponse = MutableLiveData<StatusMessage>()


    val otpVerificationData: LiveData<UserDetail>
        get() = otpVerificationResponse

    val otpSendData: LiveData<StatusMessage>
        get() = otpSendResponse



    fun verifyMobile(mobile: String, otp: String)
    {
        val verifyCall = apiInterface.verifyOtp(mobile, otp)
        MyApp.MySingleton.callApi(verifyCall, MyApp.MySingleton.OTP_VERIFICATION, this, context, false)
    }


    fun  verifyMobileThenLogin(data:OtpDataWithLocalWishlist)
    {
        val verifyCall = apiInterface.verifyOtpAndThenLogin(data)
        MyApp.MySingleton.callApi(verifyCall, MyApp.MySingleton.OTP_VERIFICATION, this, context, false)
    }

    fun otpSend(mobile: String)
    {
        val otpSendCall = apiInterface.otpSend(mobile)
        MyApp.MySingleton.callApi(
            otpSendCall,
            MyApp.MySingleton.OTP_SEND,
            this,
            context,
            false
        )
    }


    override fun <T : Any> onSuccess(callResponse: Response<T>, apiName: String)
    {
        when(apiName)
        {
            MyApp.MySingleton.OTP_VERIFICATION->{
                val response = callResponse as Response<UserDetail>
                otpVerificationResponse.postValue(response.body())
            }
            MyApp.MySingleton.OTP_SEND->{
                val response = callResponse as Response<StatusMessage>
                otpSendResponse.postValue(response.body())
            }
        }
    }

    override fun onFailure(message: String, apiName: String)
    {
        when(apiName)
        {
            MyApp.MySingleton.OTP_VERIFICATION->{
                otpVerificationResponse.postValue(UserDetail(message,false))
            }
            MyApp.MySingleton.OTP_SEND->{
                otpSendResponse.postValue(StatusMessage(false,message))
            }
        }

    }

}