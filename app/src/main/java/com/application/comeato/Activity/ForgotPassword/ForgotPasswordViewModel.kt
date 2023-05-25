package com.application.comeato.Activity.ForgotPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.application.comeato.models.StatusMessage

class ForgotPasswordViewModel(val repository: ForgotPasswordRepository):ViewModel()
{

    val resetPasswordResponse:LiveData<StatusMessage>
        get() = repository.resetPasswordLiveData


}