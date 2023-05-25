package com.comeato.Utilities

import android.app.Application
import android.content.Context
import com.comeato.ApiService.ApiInstance
import com.comeato.ApiService.ApiInterface
import retrofit2.create

class MyApp : Application()
{
    override fun onCreate() {
        super.onCreate()
         MySingleton.init(applicationContext)
    }

    class MySingleton {
        companion object {
            private lateinit var appContext: Context

            fun init(context: Context) {
                appContext = context.applicationContext
            }

            fun getAppContext(): Context
            {
                return appContext
            }
            public fun getApiInterface(): ApiInterface
            {
                return ApiInstance.instance().create(ApiInterface::class.java);
            }
        }
    }
}