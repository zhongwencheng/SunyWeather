package com.example.sunyweather.logic.network

import com.example.sunyweather.SunyWeatherApplication
import com.example.sunyweather.logic.model.DailyResponse
import com.example.sunyweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
    @GET("v2.5/${SunyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng:String,@Path("lat")lat:String):Call<RealtimeResponse>
    @GET("v2.5/${SunyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng:String,@Path("lat")lat:String):Call<DailyResponse>
}