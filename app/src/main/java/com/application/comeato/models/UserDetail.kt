package com.application.comeato.models

data class UserDetail(
    val access_token: String?,
    val message: String,
    val status: Boolean,
    val user: User?
)
{
    constructor(message: String,status: Boolean):this(null,message,status,null)
}