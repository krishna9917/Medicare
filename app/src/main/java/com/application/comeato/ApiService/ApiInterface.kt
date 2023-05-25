package com.comeato.ApiService

import com.application.comeato.PaymentGateway.CCAvenue.CCAvenue
import com.application.comeato.models.CCAvenueResponse
import com.application.comeato.models.Categories
import com.application.comeato.models.DataForGetMyDeals
import com.application.comeato.models.DealCode
import com.application.comeato.models.HomeData
import com.application.comeato.models.LoginDataWithLocalWishList
import com.application.comeato.models.MyDealsData
import com.application.comeato.models.OfferData
import com.application.comeato.models.OtpDataWithLocalWishlist
import com.application.comeato.models.UserDetail
import com.application.comeato.models.PropertyDetail
import com.application.comeato.models.SearchPropertyData
import com.application.comeato.models.StatusMessage
import com.application.comeato.models.Subscription
import com.application.comeato.models.WishList
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiInterface {


    @GET("home")
    fun getHomeData(): Call<HomeData>

    @FormUrlEncoded
    @POST("property")
    fun getPropertyDetail(@Field("id") id: String): Call<PropertyDetail>


    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("mobile") mobile: String,
        @Field("password") password: String,
        @Field("confirm_password") confirm_password: String
    ): Call<StatusMessage>


    @POST("login")
    fun login(@Body loginDataWithLocalWishList: LoginDataWithLocalWishList): Call<UserDetail>


    @FormUrlEncoded
    @POST("verify-otp")
    fun verifyOtp(@Field("mobile") mobile: String, @Field("otp") otp: String): Call<UserDetail>


    @POST("verify-otp")
    fun verifyOtpAndThenLogin(@Body otpDataWithLocalWishList: OtpDataWithLocalWishlist): Call<UserDetail>

    @FormUrlEncoded
    @POST("send-otp")
    fun otpSend(@Field("mobile") mobile: String): Call<StatusMessage>


    @POST("profile")
    fun getUserProfile(): Call<UserDetail>


    @Multipart
    @POST("update_profile")
    fun updateProfile(
        @Part("name") name: RequestBody,
        @Part profile_pic: MultipartBody.Part
    ): Call<UserDetail>


    @FormUrlEncoded
    @POST("forgot_password")
    fun resetPassword(
        @Field("mobile") mobile: String,
        @Field("new_password") new_password: String,
        @Field("confirm_new_password") confirm_new_password: String
    ): Call<StatusMessage>


    @FormUrlEncoded
    @POST("generate_coupon")
    fun showDealCode(
        @Field("property_id") property_id: String,
        @Field("deal_id") deal_id: String
    ): Call<DealCode>


    @GET("search")
    fun search(
        @Query("search") search: String,
        @Query("page") page: String,
        @Query("type") type: String,
        @Query("category_id") category_id: String
    ): Call<SearchPropertyData>


    @GET("categories")
    fun propertyCategories(): Call<Categories>


    @POST("wishlist")
    fun getWishList(): Call<WishList>


    @FormUrlEncoded
    @POST("add_wishlist")
    fun addWishList(@Field("property_id") property_id: String): Call<StatusMessage>


    @FormUrlEncoded
    @POST("remove_wishlist")
    fun removeWishList(@Field("property_id") property_id: String): Call<StatusMessage>


    @FormUrlEncoded
    @POST("contact_us")
    fun contactUs(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("mobile") mobile: String,
        @Field("message") message: String
    ): Call<StatusMessage>


    @POST("my_deals")
    fun getMyDeals(@Body data: DataForGetMyDeals): Call<MyDealsData>


    @GET("offers")
    fun getOffers(): Call<OfferData>


    @POST("subscriptions")
    fun getSubscriptionPlan(): Call<Subscription>


    @FormUrlEncoded
    @POST("cc_avenue_gateway_encryption")
    fun getCcVenuePaymentUrl(@Field("main_plan_id")mainPlanId:String,@Field("sub_plane_id")subPlanId:String): Call<CCAvenueResponse>

}

