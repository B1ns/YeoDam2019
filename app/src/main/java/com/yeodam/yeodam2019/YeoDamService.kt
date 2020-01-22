package com.yeodam.yeodam2019

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.yeodam.yeodam2019.App.Companion.CHANNEL_ID
import com.yeodam.yeodam2019.App.Companion.CHANNEL_NAME
import com.yeodam.yeodam2019.view.activity.map.MapActivity
import java.io.Serializable


open class YeoDamService : Service(), Serializable {

    var mapLatitude = ArrayList<String>()
    var mapLongitude = ArrayList<String>()

    var mapMemo = ArrayList<String>()
    var mapMemoLocation = ArrayList<LatLng>()

    var mapPhoto = ArrayList<Bitmap>()
    var mapPhotoLocation = ArrayList<LatLng>()

    var mapPay = ArrayList<String>()
    var mapPayInfo = ArrayList<String>()
    var mapPayLocation = ArrayList<LatLng>()


    var lat: Double = 0.0
    var lon: Double = 0.0
    var count: Int = 0
    var startYeoDam = false

    // data

    var myLatitude = ArrayList<Double>()
    var myLongitude = ArrayList<Double>()

    // 바인더 객체

    private val localBinder = LocalBinder()

    // Location

    lateinit var locationCallback: MyLocationCallback
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest

    // context

    private lateinit var mContext: Context

    fun contextInit(context: Context) {
        mContext = context
    }

    override fun onCreate() {
        super.onCreate()

        val notificationIntent = Intent(this, MapActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val builder: NotificationCompat.Builder

        // 안드로이드 O 버전 이상에서부터는 채널 ID를 지정해주어야 합니다.
        builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )

            NotificationCompat.Builder(this, CHANNEL_ID)

        } else {

            NotificationCompat.Builder(this)

        }

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_logo_yeodam)

        val notification = builder
            .setLargeIcon(bitmap)
            .setSmallIcon(R.drawable.ic_logo_yeodam)
            .setContentTitle("여담")
            .setContentText("지금은 여행을 기록중입니다:D")
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)

    }

    override fun onDestroy() {
        super.onDestroy()

    }

    fun locationInit() {

        fusedLocationProviderClient = FusedLocationProviderClient(mContext)

        locationCallback = MyLocationCallback()

        locationRequest = LocationRequest()

        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        locationRequest.interval = 200000

        locationRequest.fastestInterval = 60000

        addLocationListener()
    }

    fun addLocationListener() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    inner class MyLocationCallback : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation

            var firstLocationLat: Double
            var firstLocationLog: Double

            val latB = 0.009609
            val lonB = 0.00594

            location?.run {

                firstLocationLat = lat
                firstLocationLog = lon

                lat = latitude
                lon = longitude


//                if (((lat - firstLocationLat) < latB && (lon - firstLocationLog) < lonB)) {
//                    Log.d("Yeodam", "최소 범위를 넘어서지 않았습니다.")
//                } else {
//                    myLatitude.add(lat)
//                    myLongitude.add(lon)
//                }

                myLatitude.add(lat)
                myLongitude.add(lon)

                Log.d("Hello", "$count | $lat, $lon")

                Log.d("World", "$count | ${myLatitude[count]}")
                Log.d("World", "$count | ${myLongitude[count]}")

                count++


                val messageReceiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context, intent: Intent) {

                        Log.d("Service", " messageReceiver OK")

                        val onStartAction = intent.getStringExtra("OK")

                        if (onStartAction == "on_Start_OK") {

                            Log.d("sendLocation", "OK")
                            Log.d("sendLocation", "| { $myLatitude, $myLongitude } ")
                            val localBroadcastManager = LocalBroadcastManager.getInstance(mContext)
                            val putIntent = Intent("intent_action")
                            putIntent.putExtra("lat", myLatitude)
                            putIntent.putExtra("lon", myLongitude)
                            localBroadcastManager.sendBroadcast(putIntent)
                        }
                    }
                }

                LocalBroadcastManager.getInstance(mContext)
                    .registerReceiver(messageReceiver, IntentFilter("map_action"))

            }

        }
    }

    override fun onBind(intent: Intent?): IBinder? {

        return localBinder
    }

    inner class LocalBinder : Binder() {
        fun getService(): YeoDamService = this@YeoDamService
    }

}
