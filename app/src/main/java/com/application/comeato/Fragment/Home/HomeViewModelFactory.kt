package com.comeato.Fragment.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeViewModelFactory(private val homeRepository: HomeRepository):ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  HomeViewModel(homeRepository) as T
    }
}