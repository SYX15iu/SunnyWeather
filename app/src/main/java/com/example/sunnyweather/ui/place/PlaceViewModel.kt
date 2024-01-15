package com.example.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunnyweather.logic.Repository
import com.example.sunnyweather.logic.model.Place

class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()
//    对界面显示的城市数据进行缓存，原则上和界面相关的数据都应该放在viewmodel
    val placeList = ArrayList<Place>()
//    将搜索参数赋值给一个searchLiveData对象，并使用transformations.swich()将对象转换为一个可以观察的对象
val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
    Repository.searchPlaces(query)
}
    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    fun savePlace(place: Place)=Repository.savePlace(place)
    fun getsavedPlace()=Repository.getsavedPlace()
    fun isPlaceSaved()=Repository.isPlaceSaved()
}