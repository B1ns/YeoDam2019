package com.yeodam.yeodam2019.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.view.activity.map.MapActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_main.setOnClickListener {
            slider.visibility = View.VISIBLE

        }
    }
}
