package com.application.comeato.Fragment.Property

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PropertyViewModelFactory(val repository: PropertyRepository):ViewModelProvider.Factory
{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PropertyViewModel(repository) as T
    }
}