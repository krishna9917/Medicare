package com.application.comeato.Interfaces

interface CurrentLocationListener
{
    fun onGetCurrentLocation(string: String)
    fun onError(string: String)
}