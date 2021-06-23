package com.example.sunyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunyWeatherApplication:Application() {

    companion object{

         @SuppressLint("StaticFileLeak")
         lateinit var context:Context
         const val TOKEN="8P2LWe7yP2xLk2zf"
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }

}