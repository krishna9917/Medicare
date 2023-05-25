package com.comeato.ApiService

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiInstance
{
    private const val BASE_URL="";

    fun instance():Retrofit
    {
        return Retrofit.Builder().baseUrl(BASE_URL).client(getHttpClient()!!).addConverterFactory(GsonConverterFactory.create()).build()
    }
    private fun getHttpClient(): OkHttpClient?
    {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient()
            .newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .addNetworkInterceptor(StethoInterceptor())
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(interceptor)
            .build()
    }
}