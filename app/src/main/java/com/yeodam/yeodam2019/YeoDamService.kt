package com.yeodam.yeodam2019

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationManager
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.maps.model.LatLng
import com.yeodam.yeodam2019.App.Companion.CHANNEL_ID
import com.yeodam.yeodam2019.App.Companion.CHANNEL_NAME
import com.yeodam.yeodam2019.view.activity.map.MapActivity


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

    // 바인더 객체

    private val locaalBinder = LocalBinder()

    // Location 객체

    lateinit var location: Location
    protected lateinit var locationManager: LocationManager

    // context

    private lateinit var mContext: Context

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

        val notification = builder
            .setContentTitle("여담")
            .setContentText("지금은 여행을 기록중입니다:D")
            .setSmallIcon(R.drawable.ic_logo_yeodam)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)

    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        /* startService()를 호출하면 이 콜백 메서드가 호출이 됩니다. 이 부분에서 Intent를 전달받습니다. */

        // MapActivity 데이터를 받아오는 작업


        super.onStartCommand(intent, flags, startId)

        return START_REDELIVER_INTENT
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


    inner class LocalBinder : Binder() {
        fun getService(): YeoDamService = this@YeoDamService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return locaalBinder
    }

    fun gpsTracker(context: Context) {
        this.mContext = context
//        tracking()
    }

//    fun tracking(): Location {
//
//        try {
//
//            locationManager = mContext.getSystemService(LOCATION_SERVICE) as LocationManager
//
//            val isGPSEnabled =
//                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//            val isNetworkEnabled =
//                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//
//
//
//
//        } catch (e: Exception) {
//
//        }
////        return location
//    }

}
