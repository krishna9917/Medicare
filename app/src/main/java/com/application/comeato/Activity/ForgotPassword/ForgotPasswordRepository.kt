package com.application.comeato.Activity.ForgotPassword

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.comeato.Interfaces.ApiCallListener
import com.application.comeato.models.StatusMessage
import com.comeato.ApiService.ApiInterface
import com.comeato.Utilities.MyApp
import retrofit2.Response

class ForgotPasswordRepository(private val apiInterface: ApiInterface, val context:Context):ApiCallListener
{

   private val resetPasswordResponse= MutableLiveData<StatusMessage>()

   val resetPasswordLiveData :LiveData<StatusMessage>
       get() = resetPasswordResponse



    fun resetPassword(mobile:String,password:String,confirm_password:String)
    {
        val resetPasswordCall = apiInterface.resetPassword(mobile,password,confirm_password)
        MyApp.MySingleton.callApi(resetPasswordCall,MyApp.MySingleton.RESET_PASSWORD,this,context,false)
    }

    override fun <T : Any> onSuccess(callResponse: Response<T>, apiName: String)
    {
        val response =callResponse as Response<StatusMessage>
        resetPasswordResponse.postValue(response.body())
    }

    override fun onFailure(message: String, apiName: String) {
        resetPasswordResponse.postValue(StatusMessage(false,message))
    }

}