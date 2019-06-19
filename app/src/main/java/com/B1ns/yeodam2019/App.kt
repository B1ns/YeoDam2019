package com.B1ns.yeodam2019

import android.app.Application

class App : Application(){

    companion object{
        lateinit var prefs : MySharedPreferences
    }

    override fun onCreate() {
        prefs = MySharedPreferences(applicationContext)
        super.onCreate()
    }
}