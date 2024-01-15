package com.example.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName
//主要是定义的类和属性
data class PlaceResponse(val status:String,val places:List<Place>)

//地点的属性，@SerializedName这个表示将括号外边的address替换成formatted_address
data class Place(val name:String,val location:Location,@SerializedName("formatted_address") val address:String)

//经纬度
data class Location(val lng:String,val lat:String)