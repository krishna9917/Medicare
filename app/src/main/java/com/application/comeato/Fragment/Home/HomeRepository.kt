package com.comeato.Fragment.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.comeato.models.StatusMessage
import com.comeato.ApiService.ApiInterface

class HomeRepository(private val apiInterface:ApiInterface)
{
    private val homeDataLiveData = MutableLiveData<StatusMessage>()

     val homeData :LiveData<StatusMessage>
     get() = homeDataLiveData

    suspend fun getHomeData()
    {
        val result = apiInterface.getHomeData()
        if(result.body()!=null)
        {
            homeDataLiveData.postValue(result.body())
        }
    }




}