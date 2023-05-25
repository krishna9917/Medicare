package com.application.comeato.Activity.CustomerSupport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SupportViewModelFactory(val repository: SupportRepository):ViewModelProvider.Factory
{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SupportViewModel(repository) as T
    }

}