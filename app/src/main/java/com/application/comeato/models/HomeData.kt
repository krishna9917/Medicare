package com.application.comeato.models

class HomeData(val topBanner:ArrayList<ImageData>, val category:ArrayList<Category>, val brandBanner:ArrayList<ImageData>, val nearDeals:ArrayList<Property>, val topBrand:ArrayList<Property>, val dayDeals:ArrayList<Property>, val nearLocations:ArrayList<NearLocationData>)
{
    constructor() : this(ArrayList(), ArrayList(),ArrayList(),ArrayList(), ArrayList(),ArrayList(),ArrayList())

}
