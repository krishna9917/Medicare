package com.application.comeato.models

data class DealCode(
    val coupon: Coupon?,
    val message: String,
    val error_code:Int,
    val status: Boolean
)