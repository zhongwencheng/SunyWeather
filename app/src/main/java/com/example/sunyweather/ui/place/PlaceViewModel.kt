package com.example.sunyweather.ui.place

import android.app.DownloadManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunyweather.logic.Repository
import com.example.sunyweather.logic.model.Place

class PlaceViewModel:ViewModel(){

    private val searchLiveData = MutableLiveData<String>()
    val placeList = ArrayList<Place>()
    val placeLiveData = Transformations.switchMap(searchLiveData){
        query->Repository.searchPlaces(query)
    }
    fun searchPlaces(query: String){
        searchLiveData.value=query
    }
}