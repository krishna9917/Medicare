package com.application.comeato.models

data class PropertyX(
    val address: String,
    val category_id: Int,
    val category_name: String,
    val city_name: String,
    val details: String,
    val email: String,
    val featured_image_url: String,
    val gallery: List<String>,
    val id: Int,
    val locality_name: String,
    val mobile: String,
    val name: String,
    val slug: String,
    val state_name: String,
    val tabs: List<Tab>,
    val similar_properties:List<FeaturedProperty>,
    val is_in_wishlist:Boolean
)