package com.comeato.Fragment.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.comeato.models.StatusMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeViewModel(private val homeRepository: HomeRepository):ViewModel()
{

    init
    {
       viewModelScope.launch(Dispatchers.IO)
       {
           homeRepository.getHomeData()
       }
    }

    val getHomeData:LiveData<StatusMessage>
    get() = homeRepository.homeData


}