package com.yeodam.yeodam2019

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.yeodam.yeodam2019.App.Companion.CHANNEL_ID
import com.yeodam.yeodam2019.view.activity.map.MapActivity

open class YeoDamService : Service() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: MyLocationCallBack

    var mapLatitude = ArrayList<String>()
    var mapLongitude = ArrayList<String>()

    var mapMemo = ArrayList<String>()
    var mapMemoLocation = ArrayList<LatLng>()

    var mapPhoto = ArrayList<Bitmap>()
    var mapPhotoLocation = ArrayList<LatLng>()

    var mapPay = ArrayList<String>()
    var mapPayInfo = ArrayList<String>()
    var mapPayLocation = ArrayList<LatLng>()

    var lastLatitude: Double = 0.0
    var lastLongitude: Double = 0.0

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

    override fun onCreate() {
        super.onCreate()

        locationInit()

        val notificationIntent = Intent(this, MapActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("여담")
            .setContentText("지금은 여행을 기록중입니다:D")
            .setSmallIcon(R.drawable.ic_logo_yeodam)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 서비스 처음 시작시 할 동작 정의



        return START_NOT_STICKY
    }

    override fun onDestroy() {
        // 서비스 종료시 할 것들 정리
        val localBroadcastManager = LocalBroadcastManager.getInstance(this)
        val intent = Intent("intent_action")

        intent.putExtra("mapLatitude", mapLatitude)
        intent.putExtra("mapLongitude", mapLongitude)

        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {


        return null
    }

    open inner class MyLocationCallBack : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation

            location?.run {

                lastLatitude = latitude
                lastLongitude = longitude

                mapLatitude.add(latitude.toString())
                mapLongitude.add(longitude.toString())

            }
        }
    }

    open fun mapLine(): LatLng = LatLng(lastLatitude, lastLongitude)

}