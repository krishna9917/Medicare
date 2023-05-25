package com.application.comeato.models

class PropertyData(val sliderImages:ArrayList<ImageData>,val propertyService:ArrayList<PropertyServices>, val similarProperty:ArrayList<Property>)
{
    constructor():this(ArrayList(),ArrayList(),ArrayList())
}