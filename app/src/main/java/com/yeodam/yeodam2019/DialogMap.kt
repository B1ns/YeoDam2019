package com.yeodam.yeodam2019

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.activity_dialog_map.*


class DialogMap : AppCompatActivity(), OnMapReadyCallback {

    private var YeoDam = ArrayList<LatLng>()

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_dialog_map)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.dialog_map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        getMapData()

        mapLine()

        buttonListener()

    }

    private fun buttonListener() {

        dialog_yes.setOnClickListener {

            finish()
        }

        dialog_no.setOnClickListener {
            finish()
        }
    }

    private fun mapLine() {

        for (i in YeoDam) {
            mMap.addPolyline(PolylineOptions().add(i))
        }
    }

    private fun getMapData() {

        val intent = Intent()

        YeoDam = intent.getSerializableExtra("MapData") as ArrayList<LatLng>


        var story_day_data = intent.getStringExtra("story_day")
        story_day.text = story_day_data

        var photoCount = intent.getStringExtra("photoCount")
        dialog_photo.text = photoCount

        var memoCount = intent.getStringExtra("memoCount")
        dialog_edit.text = memoCount

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val latLng = YeoDam[0]
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

    }

}
