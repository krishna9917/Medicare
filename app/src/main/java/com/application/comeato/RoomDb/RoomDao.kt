package com.application.comeato.RoomDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.application.comeato.models.FeaturedProperty
import com.application.comeato.models.WishListData

@Dao
interface RoomDao
{

    @Query("SELECT * FROM WishList")
    fun getWishList(): LiveData<List<WishListData>>


    @Insert
    suspend fun addWishList(wishList:WishListData)


    @Delete
    suspend fun removeWishList(wishList:WishListData)


    @Query("SELECT * FROM WishList WHERE property_id =:propertyId")
    suspend fun getWishListById(propertyId:String): WishListData


    @Query("DELETE FROM WishList")
    fun deleteWishLists()




}