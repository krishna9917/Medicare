package com.application.comeato.PaymentGateway.CCAvenue

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.comeato.Interfaces.ApiCallListener
import com.application.comeato.models.CCAvenueResponse
import com.comeato.Utilities.MyApp
import retrofit2.Response

class CCAvenueRepository(val context: Context) :ApiCallListener
{
    private val ccAvenueData = MutableLiveData<CCAvenueResponse>()

    val ccAvenueResponse: LiveData<CCAvenueResponse>
        get() = ccAvenueData


    fun getCcAvenuePaymentUrl(mainPlanId:String,subPlanId:String)
    {
        val paymentCall = MyApp.MySingleton.getApiInterface().getCcVenuePaymentUrl(mainPlanId,subPlanId)
        MyApp.MySingleton.callApi(paymentCall,MyApp.MySingleton.GET_CC_AVENUE_URL,this,context,false)
    }


    override fun <T : Any> onSuccess(callResponse: Response<T>, apiName: String) {
        when(apiName)
        {
            MyApp.MySingleton.GET_CC_AVENUE_URL -> {
                val response  = callResponse as Response<CCAvenueResponse>
                ccAvenueData.postValue(response.body())
            }
        }

    }

    override fun onFailure(message: String, apiName: String) {

    }


}