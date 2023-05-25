package com.application.comeato.Activity.WishList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WishListViewModelFactory(val repository: WishListRepository):ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WishListViewModel(repository) as T
    }
}