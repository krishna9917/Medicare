package com.application.comeato.models

data class CustomerDetail(
    val address: String,
    val email: String,
    val phone: String
)
{
    constructor():this("","","")
}
