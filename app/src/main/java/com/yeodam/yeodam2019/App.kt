package com.yeodam.yeodam2019

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class App : Application() {

    companion object {
        lateinit var prefs: MySharedPreferences
        const val CHANNEL_ID = "YeodamService"
        const val CHANNEL_NAME = "YeodamServcice"
    }

    override fun onCreate() {
        prefs = MySharedPreferences(applicationContext)
        super.onCreate()

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "YeoDam Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)

        }
    }
}