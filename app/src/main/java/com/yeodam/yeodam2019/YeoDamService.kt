package com.yeodam.yeodam2019

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.yeodam.yeodam2019.App.Companion.CHANNEL_ID
import com.yeodam.yeodam2019.view.activity.map.MapActivity

class YeoDamService : Service() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val notificationIntent = Intent(this, MapActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0 , notificationIntent, 0)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("여담")
            .setContentText("지금은 여행을 기록중입니다:D")
            .setSmallIcon(R.drawable.ic_logo_yeodam)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {

        return null
    }

}