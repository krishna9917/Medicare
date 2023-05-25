package com.application.comeato.Activity.PropertyDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PropertyDetailViewModelFactory(val propertyDetailRepository: PropertyDetailRepository): ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PropertyDetailViewModel(propertyDetailRepository) as T
    }
}