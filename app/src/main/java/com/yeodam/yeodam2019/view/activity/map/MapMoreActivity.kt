package com.yeodam.yeodam2019.view.activity.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yeodam.yeodam2019.R
import kotlinx.android.synthetic.main.appbar_more.*

class MapMoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_more)

        buttonListener()
    }

    private fun buttonListener() {
        more_back.setOnClickListener {
            onBackPressed()
        }

        more_map.setOnClickListener {
            onBackPressed()
        }
    }
}
