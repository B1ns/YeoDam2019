package com.yeodam.yeodam2019.view.activity.main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.view.activity.setting.SettingActivity
import com.yeodam.yeodam2019.view.activity.map.MapActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.image
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private var fab: Boolean = false
    private var itemType: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonListener()

        slider()

    }

    @SuppressLint("RestrictedApi")
    private fun buttonListener() {

        fab_main.setOnClickListener {

            slider.visibility = View.VISIBLE

            fab = true

            if (fab) {

                fab_main.visibility = View.INVISIBLE
            }
        }

        setting_btn.setOnClickListener {
            startActivity<SettingActivity>()
        }

        change_Item.setOnClickListener {

            itemType = if (itemType) {
                change_Item.setBackgroundResource(R.drawable.ic_main_list)
                false
            } else {
                change_Item.setBackgroundResource(R.drawable.ic_main_list)
                true
            }
        }

    }

    private fun slider() {
        if (slider.isCompleted()) {
            Log.d("fuck", "wht")
            startMap()
        }
    }

    private fun startMap() {
        slider.visibility = View.GONE
        startActivity<MapActivity>()
    }

}