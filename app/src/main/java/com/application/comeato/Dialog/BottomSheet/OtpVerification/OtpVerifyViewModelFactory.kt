package com.application.comeato.Dialog.BottomSheet.OtpVerification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OtpVerifyViewModelFactory(val repository: OtpVerifyRepository):ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OtpVerifyViewModel(repository) as T
    }

}