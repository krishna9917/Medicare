package com.application.comeato.models

data class AnnualPlane(
    val amount: String,
    val id: Int,
    val is_membership_active: Boolean,
    val title: String
)
{
    constructor():this("",0,false,"")
}