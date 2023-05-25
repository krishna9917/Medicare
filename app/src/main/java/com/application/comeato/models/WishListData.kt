package com.application.comeato.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("wishList")
class WishListData(val image_url: String,@PrimaryKey val property_id: Int, val locality: String, val name: String)