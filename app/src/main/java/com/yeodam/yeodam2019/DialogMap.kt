package com.yeodam.yeodam2019

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.yeodam.yeodam2019.view.activity.map.MapActivity

class DialogMap : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_map)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        getYeodam()
    }

    private fun getYeodam() {
        for (i in MapActivity().yeodamList) {
            mMap.addPolyline(PolylineOptions().add(i))
        }

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        var latLng = MapActivity().yeodamList[0]
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

    }


}
