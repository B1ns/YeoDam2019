package com.yeodam.yeodam2019.view.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.yeodam.yeodam2019.R

class loadMapActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_map)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


    }
}
