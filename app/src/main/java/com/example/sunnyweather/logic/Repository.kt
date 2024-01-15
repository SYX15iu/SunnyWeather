package com.example.sunnyweather.logic

import android.app.appsearch.SearchResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.sunnyweather.logic.dao.PlaceDao
import com.example.sunnyweather.logic.model.Place
import com.example.sunnyweather.logic.model.Weather
import com.example.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

//创建单例类作为仓库层的同一封装入口
object Repository {
//    //liveData 函数提供挂起函数的上下文，可以在liveData函数调用任意过期的函数
//    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
//        val result = try {
////        这里调用的是SunnyWeatherNetwork的searchPlaces（）函数来搜索城市数据
//            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
//            if (placeResponse.status == "ok") {
//                val places = placeResponse.places
////            kotlin内置的Result.success（）方法包装获取的城市数据列表
//                Result.success(places)
//            } else {
//                Result.failure(RuntimeException("response status is${placeResponse.status}"))
//            }
//        } catch (e: Exception) {
//            Result.failure<List<Place>>(e)
//        }
////    emit是将包装的结果发射出去
//        emit(result)
//    }
fun searchPlaces(query: String) = fire(Dispatchers.IO) {
    val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
    if (placeResponse.status == "ok") {
        val places = placeResponse.places
        Result.success(places)
    } else {
        Result.failure(RuntimeException("response status is ${placeResponse.status}"))
    }
}

//    获取天气信息
    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
//    coroutineScope用于声明协程的作用域
        coroutineScope {
//            async，await等关键字是异步
            val deferredRealtime = async {
                SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {

                val weather = Weather(realtimeResponse.result.realtime,
                    dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) = liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

//    实现封装好的PlaceDao,封装PlaceDao
    fun savePlace(place:Place)=PlaceDao.savePlace(place)
    fun getsavedPlace()=PlaceDao.getSavePlace()
    fun isPlaceSaved()=PlaceDao.isPlaceSaved()


}









