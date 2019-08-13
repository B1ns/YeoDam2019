package com.yeodam.yeodam2019.view.activity.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gdacciaro.iOSDialog.iOSDialogBuilder
import com.yeodam.yeodam2019.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.yeodam.yeodam2019.toast
import com.yeodam.yeodam2019.view.activity.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_map_activity.*
import kotlinx.android.synthetic.main.appbar.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private val REQUEST_ACCESS_FINE_LOCATION = 1000

    private val polylineOptions = PolylineOptions().width(10f).color(Color.argb(50, 89, 211, 238))

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: MyLocationCallback

    private lateinit var fab_open: Animation
    private lateinit var fab_close: Animation

    private var start = true
    private var story = true
    private var isFabOpen = false

    private var yeodam: ArrayList<Any>? = null

    private var myLatitude: Double = 0.0
    private var myLongitude: Double = 0.0

    private var countDay = 0
    private var countToday = 0
    private var countLastday = 0

    private var travelStart = ""
    private var travelEnd = ""

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_activity)

        // 화면이 꺼지지 않게 하기
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        //SupportMapFragment를 가져와서 지도가 준비되면 알림을 받습니다.

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        locationInit()

        fab()

        mapHome_btn.setOnClickListener {

            if (story) {

                iOSDialogBuilder(this@MapActivity)
                    .setTitle("여행중 입니다.")
                    .setSubtitle("여행을 종료하시겠습니까?")
                    .setBoldPositiveLabel(true)
                    .setCancelable(false)
                    .setPositiveListener("네") { dialog ->
                        toast("취소됬습니다 !")
                        //여행 취소 로직 작성 구간
                        toggleFab()
                        dialog.dismiss()
                    }
                    .setNegativeListener(
                        getString(R.string.dismiss)
                    ) { dialog -> dialog.dismiss() }
                    .build().show()

                main_background.visibility = View.GONE
                startActivity<MainActivity>()
                finish()
            } else {
                startActivity<MainActivity>()
                finish()
            }
        }

    }

    @SuppressLint("NewApi")
    fun date() {
        countDay = countLastday - countToday
        count_day.setText(countDay)
    }


    /*
    *  Start double floating action button
    */

    private fun fab() {
        fabInit()

        // 각각의 서브 fab

        fab_cancle.setOnClickListener {

            iOSDialogBuilder(this@MapActivity)
                .setTitle("정말 여행을 취소 하시겠습니까?")
                .setSubtitle("여행했던 경로, 메모, 경비, 포함한 모든 정보들은 취소 됩니다.")
                .setBoldPositiveLabel(true)
                .setCancelable(false)
                .setPositiveListener("네") { dialog ->
                    toast("취소됬습니다 !")
                    //여행 취소 로직 작성 구간
                    toggleFab()
                    dialog.dismiss()
                }
                .setNegativeListener(
                    getString(R.string.dismiss)
                ) { dialog -> dialog.dismiss() }
                .build().show()

        }


        fab_pause.setOnClickListener {
            toggleFab()
            story = false
            // 최상의 fab

        }

        fab_stop.setOnClickListener {
            toggleFab()
            story = false
            // 중간의 fab

        }

        // 메인 fab 을 눌렀을시 서브 fab 이 나옴
        fab_main_map.setOnClickListener {
            toggleFab()
        }

        fab_mylocation.setOnClickListener {
            val latLng = LatLng(myLatitude, myLongitude)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

            Log.d("Map", "위도 : $myLatitude, 경도 : $myLongitude")
        }

    }

    @SuppressLint("ResourceType")
    private fun fabInit() {
        fab_open = AnimationUtils.loadAnimation(this, R.animator.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.animator.fab_close)
    }

    private fun toggleFab() {
        if (isFabOpen) {
            fab_main_map.setImageResource(R.drawable.ic_map_air)
            fab_pause.startAnimation(fab_close)
            fab_stop.startAnimation(fab_close)
            fab_cancle.startAnimation(fab_close)
            fab_pause.isClickable = false
            fab_stop.isClickable = false
            fab_cancle.isClickable = false

            isFabOpen = false

        } else {
            fab_main_map.setImageResource(R.drawable.ic_fab_close)
            fab_pause.startAnimation(fab_open)
            fab_stop.startAnimation(fab_open)
            fab_cancle.startAnimation(fab_open)
            fab_pause.isClickable = true
            fab_stop.isClickable = true
            fab_cancle.isClickable = true

            isFabOpen = true
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val korea = LatLng(37.586218, 126.975941)
        mMap.addMarker(MarkerOptions().position(korea).title("대한민국 청와대"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(korea))

        permissionCheck(cancel = {
            showPermissionInfoDialog()
        }, ok = {
            mMap.isMyLocationEnabled = true
        })
    }

    private fun locationInit() {
        fusedLocationProviderClient = FusedLocationProviderClient(this)

        locationCallback = MyLocationCallback()

        locationRequest = LocationRequest()
        //GPS 우선
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        // 업데이트 인터벌
        // 위치 정보가 없을때는 업데이트 안 함
        locationRequest.interval = 10000

        // 정확함. 이것보다 짧은 업데이트는 하지 않음
        locationRequest.fastestInterval = 5000


    }

    private fun showPermissionInfoDialog() {
        alert("현재 위치 정보를 얻기 위해서 위치 권한이 필요합니다.", "권한이 필요한 이유") {
            yesButton {
                ActivityCompat.requestPermissions(
                    this@MapActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION
                )
            }

            noButton {
                toast("거절")
            }
        }.show()
    }

    override fun onResume() {
        super.onResume()
        addLocationListener()
    }

    override fun onPause() {
        super.onPause()
        removeLocationListener()
    }

    @SuppressLint("MissingPermission")
    private fun addLocationListener() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun removeLocationListener() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun permissionCheck(cancel: () -> Unit, ok: () -> Unit) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                cancel()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION
                )
            }
        } else {
            ok()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_ACCESS_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    addLocationListener()
                } else {
                    //권한 거부
                    toast("권한 거부 됨")
                }
                return
            }
        }
    }

    open inner class MyLocationCallback : LocationCallback() {
        @SuppressLint("NewApi")
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation

            location?.run {

                val latLng = LatLng(latitude, longitude)

                myLatitude = latitude
                myLongitude = longitude

                if (start) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                    val current = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("dd")
                    val startTravel = DateTimeFormatter.ofPattern("yyyy/MM/dd")

                    travelStart = current.format(startTravel)
                    countToday = current.format(formatter).toInt()
                    start = false
                }

                Log.d("MapsActivity", "위도 : $latitude, 경도 : $longitude")
                if (story) {


                    polylineOptions.add(latLng)
                    //선 그리기
                    mMap.addPolyline(polylineOptions)

                    yeodam?.add(latLng)

                    Log.d("MapsActivity", "$yeodam")
                }

            }
        }
    }

}