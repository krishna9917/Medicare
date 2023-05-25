package com.application.comeato.Interfaces

import retrofit2.Call
import retrofit2.Response

interface ApiCallListener
{
    fun<T : Any> onSuccess(callResponse:Response<T>,apiName:String)
    fun onFailure(message:String,apiName:String)
}