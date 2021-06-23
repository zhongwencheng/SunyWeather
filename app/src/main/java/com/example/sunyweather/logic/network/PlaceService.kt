package com.example.sunyweather.logic.network

import com.example.sunyweather.SunyWeatherApplication
import com.example.sunyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService{

    @GET("v2/place?token=${SunyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlace(@Query("query")query: String):Call<PlaceResponse>
}