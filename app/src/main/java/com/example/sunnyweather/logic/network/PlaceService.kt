package com.example.sunnyweather.logic.network

import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//首先定义一个用于访问彩云天气搜索api的Retrofit接口
interface PlaceService {

//    当调用searchPlace方法的时候，Retorfit自动发送一个GET请求，去匹配@GET注释的地址
@GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}