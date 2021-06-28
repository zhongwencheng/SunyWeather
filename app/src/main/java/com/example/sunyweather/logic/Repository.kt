package com.example.sunyweather.logic


import android.content.Context
import androidx.lifecycle.liveData
import com.example.sunyweather.logic.dao.PlaceDao
import com.example.sunyweather.logic.model.Place
import com.example.sunyweather.logic.model.Weather
import com.example.sunyweather.logic.network.SunnyWeatherNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

object Repository {

    fun savePlace(place:Place)=PlaceDao.saveSave(place)
    fun getSavedPlace() = PlaceDao.getSavedPlace()
    fun isSavedPlace() = PlaceDao.isPlaceSave()
    fun searchPlaces(query: String) = fire(Dispatchers.IO){
    val placeResponse = SunnyWeatherNetWork.searchPlaces(query)
    if (placeResponse.status=="ok"){
                val places = placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is${placeResponse.status}"))
            }
     }
     fun refreshWeather(lng:String,lat:String)= fire(Dispatchers.IO){
         coroutineScope {
             val deferredRealtime = async {
                 SunnyWeatherNetWork.getRealtimeWeather(lng, lat)
             }
             val deferredDaily = async {
                 SunnyWeatherNetWork.getDailyWeather(lng, lat)
             }
             //让数据同时返回
             val realtimeResponse = deferredRealtime.await()
             val dailyResponse = deferredDaily.await()
             if (realtimeResponse.status == "ok" && (dailyResponse.staus == "ok"||dailyResponse.staus == null)) {
                 val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                 Result.success(weather)
             } else {
                 Result.failure(
                     RuntimeException(
                         "reatime reponse status is${realtimeResponse.status}"
                                 + "daily response status is ${dailyResponse.staus}"))
             }
         }
     }
    private fun <T>fire(context: CoroutineContext,block:suspend ()->Result<T>)= liveData<Result<T>>(context) {
       val result = try {
           block()
       }catch (e:Exception){
           Result.failure<T>(e)
       }
          emit(result)
    }
}