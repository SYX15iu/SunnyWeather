package com.example.sunnyweather.logic.model


//定义Weather类用于将Realtime与Daily对象封装
class Weather (val realtime: RealtimeResponse.Realtime,val daily:DailyResponse.Daily)