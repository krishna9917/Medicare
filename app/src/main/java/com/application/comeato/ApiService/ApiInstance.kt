package com.comeato.ApiService

import com.application.comeato.LocalMemory.MyLocalMemory
import com.comeato.Utilities.MyApp
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiInstance {


    fun instance(): Retrofit {
        return Retrofit.Builder().baseUrl(MyApp.MySingleton.BASE_URL).client(getHttpClient()!!)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    private fun getHttpClient(): OkHttpClient? {
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
            .addInterceptor(HeaderInterceptor())
            .build()
    }

    private class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val originalRequest: Request = chain.request()
            val modifiedRequest: Request = originalRequest.newBuilder()
                .header(
                    "Authorization",
                    "Bearer " + MyLocalMemory.getString(MyApp.MySingleton.USER_TOKEN)
                )
                .header("Content-Type", "application/json")
                .header("api-key", "cefb8f2a-6e6d-4f5f-936e-a2accc5bb8df")
                .header("city_name", MyLocalMemory.getString(MyApp.MySingleton.CURRENT_CITY))
                .build()

            return chain.proceed(modifiedRequest)
        }
    }
}