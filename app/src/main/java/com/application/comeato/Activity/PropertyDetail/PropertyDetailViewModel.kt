package com.application.comeato.Activity.PropertyDetail
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.application.comeato.models.DealCode
import com.application.comeato.models.PropertyDetail
import com.application.comeato.models.StatusMessage


class PropertyDetailViewModel(private val propertyDetailRepository: PropertyDetailRepository):ViewModel()
{

    val propertyDetail:LiveData<PropertyDetail>
        get() = propertyDetailRepository.propertyLiveDetail


    val dealCodeData:LiveData<DealCode>
        get() = propertyDetailRepository.dealCodeData





}