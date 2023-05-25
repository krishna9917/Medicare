package com.application.comeato.models

data class SubPlane(
    val amount: String,
    val id: Int,
    val image_url: String,
    val name: String,
    val plane_banner_img: String,
    val plane_banner_img_url: String,
    val user_subscription_status: Int,
    val valid_months: String
)