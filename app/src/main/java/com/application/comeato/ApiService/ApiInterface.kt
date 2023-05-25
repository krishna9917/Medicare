package com.comeato.ApiService

import com.application.comeato.models.Property
import com.application.comeato.models.StatusMessage
import retrofit2.Response
import retrofit2.http.POST

interface ApiInterface
{
    @POST("")
    suspend fun getHomeData():Response<StatusMessage>

    @POST("")
    suspend fun getSearchData(searchText:String):Response<ArrayList<Property>>

}