package com.yeodam.yeodam2019

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.location.*
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.yeodam.yeodam2019.App.Companion.CHANNEL_ID
import com.yeodam.yeodam2019.view.activity.map.MapActivity
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import android.os.Bundle
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.location.LocationManager
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


open class YeoDamService : Service() {

    var mapLatitude = ArrayList<String>()
    var mapLongitude = ArrayList<String>()

    var mapMemo = ArrayList<String>()
    var mapMemoLocation = ArrayList<LatLng>()

    var mapPhoto = ArrayList<Bitmap>()
    var mapPhotoLocation = ArrayList<LatLng>()

    var mapPay = ArrayList<String>()
    var mapPayInfo = ArrayList<String>()
    var mapPayLocation = ArrayList<LatLng>()

    var loadMap = false

    var lastLatitude: Double = 0.0
    var lastLongitude: Double = 0.0

    private var mLocationManager: LocationManager? = null
    private val LOCATION_INTERVAL = 1000
    private val LOCATION_DISTANCE = 10f

    override fun onCreate() {
        super.onCreate()

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

        Log.d("wow", "start")

        super.onStartCommand(intent, flags, startId)

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        // 서비스 종료시 할 것들 정리
        Log.d("wow", "start")


        val intent = Intent("intent_action")

        loadMap = true

        intent.putExtra("mapLatitude", mapLatitude)
        intent.putExtra("mapLongitude", mapLongitude)
        intent.putExtra("loadMap", loadMap)

        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


}
