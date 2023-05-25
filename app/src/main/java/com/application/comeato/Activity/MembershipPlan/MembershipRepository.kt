package com.application.comeato.Activity.MembershipPlan

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.comeato.Interfaces.ApiCallListener
import com.application.comeato.models.Subscription
import com.comeato.ApiService.ApiInterface
import com.comeato.Utilities.MyApp
import retrofit2.Response

class MembershipRepository( private val apiInterface: ApiInterface,private val context: Context): ApiCallListener
{

    private val subscriptionPlans =MutableLiveData<Subscription>()

    val subscriptionPlanResponse:LiveData<Subscription>
        get() = subscriptionPlans


    public fun getMemberShipPlan()
    {
        Log.d("DATA--->", "getMemberShipPlan ")
        val subscriptionsCall = apiInterface.getSubscriptionPlan()
        MyApp.MySingleton.callApi(subscriptionsCall,MyApp.MySingleton.GET_SUBSCRIPTION_PLANS,this,context)
    }

    override fun <T : Any> onSuccess(callResponse: Response<T>, apiName: String)
    {
        Log.d("DATA--->", "getData: ")
        when(apiName)
        {
            MyApp.MySingleton.GET_SUBSCRIPTION_PLANS->{
                val response = callResponse as Response<Subscription>
                Log.d("DATA--->", "getData: "+response.body()!!.status)
                subscriptionPlans.postValue(response.body())
            }
        }
    }

    override fun onFailure(message: String, apiName: String)
    {
        Log.d("DATA--->", "error "+message)
    }

}