package com.application.comeato.PaymentGateway.CCAvenue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CCAvenueViewModelFactory(val repository: CCAvenueRepository):ViewModelProvider.Factory
{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CCAvenueViewModel(repository) as T
    }

}