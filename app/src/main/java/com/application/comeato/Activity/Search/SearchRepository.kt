package com.application.comeato.Activity.Search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.comeato.models.Property
import com.comeato.ApiService.ApiInterface

class SearchRepository(private val apiInterface: ApiInterface)
{
    private val searchLiveData = MutableLiveData<ArrayList<Property>>()

    val searchedData : LiveData<ArrayList<Property>>
        get() = searchLiveData


    suspend fun getSearchData(searchText:String)
    {
        val result = apiInterface.getSearchData(searchText)
        if(result.body()!=null)
        {
            searchLiveData.postValue(result.body())
        }
    }
}