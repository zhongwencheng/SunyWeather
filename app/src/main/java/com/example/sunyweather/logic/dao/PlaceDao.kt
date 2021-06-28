package com.example.sunyweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.sunyweather.logic.model.Place
import com.example.sunyweather.SunyWeatherApplication
import com.google.gson.Gson

object PlaceDao {
    fun saveSave(place:Place) {
        sharedPreference().edit {
            putString("place",Gson().toJson(place))
        }
    }
    fun getSavedPlace():Place{
        val placeJson = sharedPreference().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)
    }
    fun isPlaceSave() = sharedPreference().contains("place")
    private fun sharedPreference()=SunyWeatherApplication.context
        .getSharedPreferences("sunny_weather",Context.MODE_PRIVATE)

}