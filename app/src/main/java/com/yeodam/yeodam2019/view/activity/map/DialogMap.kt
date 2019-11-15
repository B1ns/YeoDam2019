package com.yeodam.yeodam2019.view.activity.map

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.yeodam.yeodam2019.R

class DialogMap : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var mapArray = ArrayList<LatLng>()
    private var test = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_finish_dialog)

//        getYeoDam()
    }

    @Suppress("UNCHECKED_CAST")
    private fun getYeoDam() {

        val intent = Intent()
        val a = intent.getSerializableExtra("Map") as ArrayList<LatLng>
        mapArray = a

        if (mapArray == a) {
            test = true
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val korea = LatLng(37.586218, 126.975941)
        mMap.addMarker(MarkerOptions().position(korea).title("대한민국 청와대"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(korea))

        if (test) {
            val lan = mapArray
            mMap.moveCamera(CameraUpdateFactory.newLatLng(lan[0]))
            for (i in lan) {
                mMap.addPolyline(PolylineOptions().add(i))
            }
            return finish()
        }

    }

}