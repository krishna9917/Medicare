package com.application.comeato.Activity.WishList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.comeato.models.StatusMessage
import com.application.comeato.models.WishListData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WishListViewModel(val repository: WishListRepository):ViewModel()
{

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getWishList()
        }
    }

    val getWishList:LiveData<List<WishListData>>
        get() = repository.getWishList



    val wishListResponse:LiveData<StatusMessage>
        get() = repository.wishListResponse


}