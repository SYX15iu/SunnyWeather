package com.example.sunnyweather.logic.dao

import android.content.Context
import android.provider.Settings.Global.putString
import androidx.core.content.edit
import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.model.Place
import com.google.gson.Gson

object PlaceDao {

//保存查询过的数据
    fun savePlace(place: Place) {
//        将place对象存储在sharePreferences文件中，将place对象转换为json字符串
        sharedPreferences().edit { putString("place", Gson().toJson(place))
        }
    }
//    将json字符串转换为place对象
    fun getSavePlace():Place{
        val placeJson= sharedPreferences().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)
    }

//    这里还提供了一个isPlaceSaved()方法，用于判断是否有数据已被存储。
    fun isPlaceSaved()= sharedPreferences().contains("place")

//    文件名叫做Sunny_weather
    private fun sharedPreferences()=SunnyWeatherApplication.context.getSharedPreferences("Sunny_weather",
        Context.MODE_PRIVATE)


}