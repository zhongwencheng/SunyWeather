package com.example.sunyweather.ui.place

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunyweather.R
import com.example.sunyweather.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.fragment_place.*


class PlaceFragment:Fragment() {
    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }
    private lateinit var adapter: PlaceAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place,container,false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        if (viewModel.isPlaceSaved()){
            val place = viewModel.getSavedPlace()
            val intent = Intent(context,WeatherActivity::class.java).apply {
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat",place.location.lat)
                putExtra("place_name",place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }
        val layoutManager = LinearLayoutManager(activity)
         recyclerView.layoutManager = layoutManager
         adapter=PlaceAdapter(this,viewModel.placeList)
         recyclerView.adapter = adapter
         searchPlaceEdit.addTextChangedListener { editable -> val content = editable.toString()
         if (content.isNotEmpty()){
             viewModel.searchPlaces(content)
         }else{
             recyclerView.visibility=View.GONE
             bgImageView.visibility = View.VISIBLE
             adapter.notifyDataSetChanged()
           }

         }
     viewModel.placeLiveData.observe(viewLifecycleOwner
         , Observer { result->
       val places = result.getOrNull()
         if (places!=null){
             recyclerView.visibility = View.VISIBLE
             bgImageView.visibility = View.GONE
             viewModel.placeList .clear()
             viewModel.placeList.addAll(places)
             adapter.notifyDataSetChanged()
         }else{
             Toast.makeText(activity,"您操作的太骚气，没能查询到任何地点",Toast.LENGTH_SHORT).show()
             result.exceptionOrNull()?.printStackTrace()
         }
     })
    }
}