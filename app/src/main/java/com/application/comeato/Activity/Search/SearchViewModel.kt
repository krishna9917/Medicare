package com.application.comeato.Activity.Search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.comeato.models.Property
import com.application.comeato.models.StatusMessage
import com.comeato.Fragment.Home.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository) :ViewModel()
{
     fun searchData(searchText:String)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            searchRepository.getSearchData(searchText)
        }
    }

    val getSearchData: LiveData<ArrayList<Property>>
        get() =  searchRepository.searchedData

}