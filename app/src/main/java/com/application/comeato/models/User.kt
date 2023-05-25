package com.application.comeato.models

data class User(
    val email: String,
    val id: Int?,
    val is_membership_active: Boolean,
    val is_mobile_verified: Boolean,
    val mobile: String,
    val name: String,
    val profile_pic:Any?,
    val profile_pic_url:String?
){
    constructor():this("",null,false,false,"","",null,null)
}