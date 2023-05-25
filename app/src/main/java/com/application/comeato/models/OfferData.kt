package com.application.comeato.models

data class OfferData(
    val message: String,
    val offers: ArrayList<Offer>,
    val status: Boolean
)