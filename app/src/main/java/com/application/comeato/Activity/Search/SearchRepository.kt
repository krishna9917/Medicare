package com.application.comeato.Activity.Search

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.comeato.Interfaces.ApiCallListener
import com.application.comeato.models.Property
import com.application.comeato.models.SearchPropertyData
import com.comeato.ApiService.ApiInterface
import com.comeato.Utilities.MyApp
import retrofit2.Call
import retrofit2.Response

class SearchRepository(private val apiInterface: ApiInterface,val context: Context):ApiCallListener
{

    private var searchCall:Call<SearchPropertyData>?=null

    private val searchLiveData = MutableLiveData<SearchPropertyData>()

    val searchedData : LiveData<SearchPropertyData>
        get() = searchLiveData


    fun getSearchData(searchText:String,searchType:String,category_id:String,requireNextPage:Boolean=false,page:Int=1,showProgress:Boolean=false)
    {
        searchCall?.cancel()
        var  searchPage=page
        if(requireNextPage)
        {
            searchPage += 1
        }
        searchCall = apiInterface.search(searchText,searchPage.toString(),searchType,category_id)
        MyApp.MySingleton.callApi(searchCall!!,MyApp.MySingleton.SEARCH_PROPERTY,this,context,showProgress)
    }


    override fun <T : Any> onSuccess(callResponse: Response<T>, apiName: String)
    {
        when(apiName)
        {
            MyApp.MySingleton.SEARCH_PROPERTY ->{
                val response = callResponse as Response<SearchPropertyData>
                searchLiveData.postValue(response.body())
            }
        }
    }

    override fun onFailure(message: String, apiName: String)
    {

    }
}