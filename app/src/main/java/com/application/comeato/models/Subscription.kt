package com.application.comeato.models

data class Subscription(
    val annual_plane: AnnualPlane,
    val message: String,
    val status: Boolean,
    val sub_plane: ArrayList<SubPlane>
)