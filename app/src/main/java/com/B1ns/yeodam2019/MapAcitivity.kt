package com.B1ns.yeodam2019

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_map_acitivity.*

class MapAcitivity : AppCompatActivity() {

    private lateinit var fab_open: Animation
    private lateinit var fab_close: Animation

    private var isFabOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_acitivity)

        fab()

    }


    private fun fab() {
        fabInit()

        // 메인 fab 을 눌렀을시 서브 fab 이 나옴
        fab_main.setOnClickListener {
            toggleFab()
        }

        // 각각의 서브 fab
        fab_sub1.setOnClickListener {
            toggleFab()
            // 최상의 fab

        }
        fab_sub2.setOnClickListener {
            toggleFab()
            // 중간의 fab

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
}
