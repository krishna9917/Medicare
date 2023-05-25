package com.application.comeato.Activity.PropertyDetail

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.comeato.Interfaces.ApiCallListener
import com.application.comeato.LocalMemory.MyLocalMemory
import com.application.comeato.R
import com.application.comeato.RoomDb.RoomDao
import com.application.comeato.models.DealCode
import com.application.comeato.models.FeaturedProperty
import com.application.comeato.models.PropertyDetail
import com.application.comeato.models.StatusMessage
import com.application.comeato.models.WishListData
import com.comeato.ApiService.ApiInterface
import com.comeato.Utilities.MyApp
import retrofit2.Response

class PropertyDetailRepository(
    private val apiInterface: ApiInterface,
    private val context: Context,
    private val dbDao: RoomDao
) : ApiCallListener {


    private val propertyData = MutableLiveData<PropertyDetail>()

    private val dealCodeResponse = MutableLiveData<DealCode>()



    val propertyLiveDetail: LiveData<PropertyDetail>
        get() = propertyData

    val dealCodeData: LiveData<DealCode>
        get() = dealCodeResponse




    fun getPropertyDetail(propertyId: String) {
        val propertyDetailCall = apiInterface.getPropertyDetail(propertyId)
        MyApp.MySingleton.callApi(
            propertyDetailCall,
            MyApp.MySingleton.PROPERTY_DETAIL,
            this,
            context
        )
    }


    fun showDealCode(propertyId: String, dealId: String) {
        val showDealCall = apiInterface.showDealCode(propertyId, dealId)
        MyApp.MySingleton.callApi(
            showDealCall,
            MyApp.MySingleton.DEAL_CODE_GENERATE,
            this,
            context,
            false
        )
    }




    override fun <T : Any> onSuccess(callResponse: Response<T>, apiName: String) {
        when (apiName) {
            MyApp.MySingleton.PROPERTY_DETAIL -> {
                val response = callResponse as Response<PropertyDetail>
                if (response.body()?.status == true) {
                    propertyData.postValue(response.body())
                }
            }

            MyApp.MySingleton.DEAL_CODE_GENERATE -> {
                val response = callResponse as Response<DealCode>
                dealCodeResponse.postValue(response.body())
            }

        }
    }

    override fun onFailure(message: String, apiName: String) {
        when (apiName) {
            MyApp.MySingleton.DEAL_CODE_GENERATE -> {
                dealCodeResponse.postValue(
                    DealCode(
                        null,
                        context.getString(R.string.something_went_wrong_retry), 405,
                        false
                    )
                )
            }

        }
    }


}