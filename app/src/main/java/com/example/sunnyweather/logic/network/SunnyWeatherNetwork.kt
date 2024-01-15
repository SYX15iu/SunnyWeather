package com.example.sunnyweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

//定义一个统一网络数据源访问入口，对所有网络请求的api进行封装
object SunnyWeatherNetwork {

//    网络数据源访问入口对新增WeatherService接口进行封装
    private val weatherService=ServiceCreator.create(WeatherService::class.java)
//搜索未来几天的天气状况
    suspend fun getDailyWeather(lng:String,lat:String)= weatherService.getDailyWeather(lng,lat).await()
//搜索当日的天气状况
    suspend fun getRealtimeWeather(lng:String,lat:String)= weatherService.getRealtimeWeather(lng,lat).await()




//    创建PlaceService接口的动态代理对象
    private val placeService = ServiceCreator.create<PlaceService>()
//suspend fun 是挂起函数，有利于实现以异步编程和并发编程,调用接口中的searchPlaces(）函数，发起搜索城市数据的请求
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null"))
                }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}





