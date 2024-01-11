package com.example.sunnyweather.logic

import android.app.appsearch.SearchResult
import androidx.lifecycle.liveData
import com.example.sunnyweather.logic.model.Place
import com.example.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

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
fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
    val result = try {
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is${placeResponse.status}"))
        }
    } catch (e: Exception) {
        Result.failure<List<Place>>(e)
    }
    emit(result)
}
}
