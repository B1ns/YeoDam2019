package com.yeodam.yeodam2019.view.activity.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.google.firebase.database.DatabaseReference
import com.yeodam.yeodam2019.toast
import com.yeodam.yeodam2019.view.activity.main.MainActivity
import kotlinx.android.synthetic.main.activity_map_activity.*
import kotlinx.android.synthetic.main.appbar.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private val REQUEST_ACCESS_FINE_LOCATION = 1000

    private val polylineOptions = PolylineOptions().width(50f).color(R.color.trackingLine)

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: MyLocationCallBack

    private lateinit var fab_open: Animation
    private lateinit var fab_close: Animation

    private var isFabOpen = false
    private var story = true

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_activity)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        fab()
        locationInit()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        mapHome_btn.setOnClickListener {
            if (story) {
                startActivity<MainActivity>()
                finish()
            } else {
                onBackPressed()
            }
        }
    }

    /*
    *  Start double floating action button
    */

    private fun fab() {
        fabInit()

        // 각각의 서브 fab
        fab_sub1.setOnClickListener {
            toggleFab()
            story = true
            // 최상의 fab

        }

        fab_sub2.setOnClickListener {
            toggleFab()
            story = false
            // 중간의 fab

        }

        // 메인 fab 을 눌렀을시 서브 fab 이 나옴
        fab_main.setOnClickListener {
            toggleFab()
        }


    }

    @SuppressLint("ResourceType")
    private fun fabInit() {
        fab_open = AnimationUtils.loadAnimation(this, R.animator.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.animator.fab_close)
    }

    private fun toggleFab() {
        if (isFabOpen) {
            fab_main.setImageResource(R.drawable.ic_add_black_24dp)
            fab_sub1.startAnimation(fab_close)
            fab_sub2.startAnimation(fab_close)
            fab_sub1.isClickable = false
            fab_sub2.isClickable = false

            isFabOpen = false

        } else {
            fab_main.setImageResource(R.drawable.ic_close_black_24dp)
            fab_sub1.startAnimation(fab_open)
            fab_sub2.startAnimation(fab_open)
            fab_sub1.isClickable = true
            fab_sub2.isClickable = true

            isFabOpen = true
        }
    }


    /*
    * Start Google Map Service
    */


    private fun locationInit() {
        fusedLocationProviderClient = FusedLocationProviderClient(this)

        locationCallback = MyLocationCallBack()

        locationRequest = LocationRequest()
        // GPS 우선
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        // 업데이트 인터벌
        // 위치 정보가 없을 때는 업데이트 안 함
        // 상황에 따라 짧아질 수 있음, 정확하지 않음
        // 다른 앱에서 짧은 인터벌로 위치 정보를 요청하면 짧아질 수 있음
        locationRequest.interval = 10000
        // 정확함. 이것보다 짧은 업데이트는 하지 않음
        locationRequest.fastestInterval = 5000
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        val seoul = LatLng(37.566535, 126.977969)
        mMap.addMarker(MarkerOptions().position(seoul).title("Seoul"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul))

        permissionCheck(cancel = {
            showPermissionInfoDialog()
        }, ok = {
            mMap.isMyLocationEnabled = true
        })
    }


    override fun onResume() {
        super.onResume()

        // 권한 요청 ⑨
        permissionCheck(cancel = {
            // 위치 정보가 필요한 이유 다이얼로그 표시 ⑩
            showPermissionInfoDialog()
        }, ok = {
            // 현재 위치를 주기적으로 요청 (권한이 필요한 부분) ⑪
            addLocationListener()
        })
    }


    @SuppressLint("MissingPermission")
    private fun addLocationListener() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
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

    override fun onPause() {
        super.onPause()
        removeLocationListener()
    }

    private fun removeLocationListener() {
        fusedLocationProviderClient. removeLocationUpdates(locationCallback)
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this@MapActivity)
        builder.setTitle("여행중입니다 !")
        builder.setIcon(R.drawable.person)
        builder.setMessage("정말로 종료하시겠습니까?")
        builder.setPositiveButton("확인") { _, _ ->
            finish()
            story = false
        }
        builder.setNegativeButton("취소", null)
        val alertDialog = builder.create()
        alertDialog.show()
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

    inner class MyLocationCallBack : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation

            location?.run {
                // 14 level로 확대하며 현재 위치로 카메라 이동
                val latLng = LatLng(latitude, longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

                Log.d("MapsActivity", "위도: $latitude, 경도: $longitude")

                polylineOptions.add(latLng)

                // 선 그리기
                mMap.addPolyline(polylineOptions)

            }

        }
    }

//    private fun bottomNavigation(){
//
//        mapNavigation.setOnNavigationItemSelectedListener {
//
//        }
//    }

}
