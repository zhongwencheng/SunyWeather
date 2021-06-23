package com.example.sunyweather.logic


import androidx.lifecycle.liveData
import com.example.sunyweather.logic.model.Place
import com.example.sunyweather.logic.network.SunnyWeatherNetWork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

object Repository {
    fun searchPlaces(query: String) = liveData(Dispatchers.IO){
        val result = try {
          val placeResponse = SunnyWeatherNetWork.searchPlaces(query)
            if (placeResponse.status=="ok"){
                val places = placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is${placeResponse.status}"))
            }
        }catch (e:Exception){
          Result.failure<List<Place>>(e)
        }
        emit(result)
    }

}