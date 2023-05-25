package com.application.comeato.Activity.CustomerSupport

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.application.comeato.models.StatusMessage

class SupportViewModel(val repository: SupportRepository):ViewModel()
{

    val  contactingResponse:LiveData<StatusMessage>
        get() = repository.contactingLiveResponse



}