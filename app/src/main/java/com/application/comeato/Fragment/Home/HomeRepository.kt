package com.comeato.Fragment.Home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.comeato.Interfaces.ApiCallListener
import com.application.comeato.LocalMemory.MyLocalMemory
import com.application.comeato.models.CustomerDetail
import com.application.comeato.models.HomeData
import com.comeato.ApiService.ApiInterface
import com.comeato.Utilities.MyApp
import retrofit2.Response

class HomeRepository(private val apiInterface:ApiInterface,private val context: Context):ApiCallListener
{
    private val homeDataLiveData = MutableLiveData<HomeData>()

     val homeData :LiveData<HomeData>
     get() = homeDataLiveData


  fun getHomeData()
    {
        val homeCall = apiInterface.getHomeData()
        MyApp.MySingleton.callApi(homeCall,MyApp.MySingleton.HOME,this,context,false)
    }

    override fun <T : Any> onSuccess(callResponse: Response<T>, apiName: String)
    {
       val response = callResponse as Response<HomeData>
       if(response.body()?.status == true)
       {
           MyLocalMemory.putObject(MyApp.MySingleton.CUSTOMER_DETAIL, response.body()!!.customer_support)
           homeDataLiveData.postValue(response.body())
       }
    }

    override fun onFailure(message: String, apiName: String)
    {

    }


}