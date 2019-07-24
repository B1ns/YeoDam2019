package com.yeodam.yeodam2019.view.activity.main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.utils.getBitmapFromView
import com.yeodam.yeodam2019.view.activity.setting.SettingActivity
import com.yeodam.yeodam2019.view.activity.map.MapActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_userinfo.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    var fab: Boolean = false

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_main.setOnClickListener {
            getBitmapFromView(main_View)
            slider.visibility = View.VISIBLE
            fab = true
            if (fab) {
                fab_main.visibility = View.INVISIBLE
            }
        }

        setting_btn.setOnClickListener {
            startActivity<SettingActivity>()
        }

        slider()

    }

    private fun slider() {
        if (slider.isCompleted()){
            Log.d("fuck", "wht")
            startMap()
        }
    }

    private fun startMap() {
        slider.visibility = View.GONE
        startActivity<MapActivity>()
    }
}