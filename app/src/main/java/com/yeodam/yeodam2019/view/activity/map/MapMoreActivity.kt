package com.yeodam.yeodam2019.view.activity.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.view.activity.main.MainActivity
import com.yeodam.yeodam2019.view.adapter.MapMoreAdapter
import kotlinx.android.synthetic.main.activity_map_more.*
import kotlinx.android.synthetic.main.appbar_more.*
import org.jetbrains.anko.startActivity

class MapMoreActivity : AppCompatActivity() {

    private val adapter by lazy { MapMoreAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_more)

        buttonListener()

        mapMoreViewPager.adapter = MapMoreActivity@ adapter

        mapMoreTab.setupWithViewPager(mapMoreViewPager)
    }

    private fun buttonListener() {
        more_back.setOnClickListener {
            startActivity<MainActivity>()
        }

        more_map.setOnClickListener {
            onBackPressed()
        }
    }
}
