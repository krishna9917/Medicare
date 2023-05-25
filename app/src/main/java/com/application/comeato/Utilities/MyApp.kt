package com.comeato.Utilities

import android.app.Application
import android.content.Context
import com.application.comeato.ApiService.ApiCall
import com.application.comeato.Interfaces.ApiCallListener
import com.comeato.ApiService.ApiInstance
import com.comeato.ApiService.ApiInterface
public class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MySingleton.init(applicationContext)
    }

    class MySingleton {
        companion object {

             const val SITE_URL = "https://comeato.com/"
            const val BASE_URL = "https://comeato.com/api/"

            var IS_PAYMENT_SUCCESS = false


            // Local storage keys
            const val CURRENT_CITY = "CURRENT_CITY"
            const val CURRENT_STATE = "CURRENT_STATE"
            const val USER_DETAILS ="USER_DETAIL"
            const val IS_LOGIN = "IS_LOGIN"
            const val USER_TOKEN = "USER_TOKEN"
            const val CUSTOMER_DETAIL = "CUSTOMER_DETAILS"
            const val NOTIFICATION_COUNT="NOTIFICATION_COUNT"







            //api names
            const val HOME= "home"
            const val PROPERTY_DETAIL="property"
            const val DEAL_CODE_GENERATE="generate_coupon"
            const val REGISTER="register"
            const val Login="login"
            const val OTP_VERIFICATION="otp_verify"
            const val OTP_SEND="otp_send"
            const val USER_PROFILE="profile"
            const val UPDATE_PROFILE="update_profile"
            const val RESET_PASSWORD="forgot_password"
            const val SEARCH_PROPERTY="search"
            const val PROPERTY_CATEGORIES="categories"
            const val Add_WISHLIST="add_wishlist"
            const val REMOVE_WISHLIST="remove_wishlist"
            const val GET_WISHLIST="wishlist"
            const val CONTACT_US="contact_us"
            const val MY_DEALS="my_deals"
            const val GET_OFFERS="offers"
            const val GET_SUBSCRIPTION_PLANS="subscriptions"
            const val GET_CC_AVENUE_URL = "cc_venue_payment_url"


            private lateinit var appContext: Context

            fun init(context: Context) {
                appContext = context.applicationContext
            }

            fun getAppContext(): Context {
                return appContext
            }


            public fun getApiInterface(): ApiInterface {
                return ApiInstance.instance().create(ApiInterface::class.java);
            }


            public fun <T : Any> callApi(
                call: retrofit2.Call<T>,
                apiName: String,
                apiCallListener: ApiCallListener,
                context: Context,
                showProgressBar: Boolean = true
            ) {
                ApiCall(call, apiName, apiCallListener, context, showProgressBar)
            }


        }
    }
}