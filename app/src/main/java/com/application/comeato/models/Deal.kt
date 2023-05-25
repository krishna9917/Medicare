package com.application.comeato.models

data class Deal(
    val amount_or_discount: String,
    val amount_or_discount_label: String,
    val details: String,
    val id: Int,
    val status_label: String,
    val terms_and_conditions: String,
    val title: String,
    val type: Int,
    val type_label: String,
    val short_description:String,
    val expiry_date_text:String,
    val maximum_use_text:String,
    var coupon:Coupon?,
    var expired:Boolean
)