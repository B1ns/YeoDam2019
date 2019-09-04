package com.yeodam.yeodam2019.view.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yeodam.yeodam2019.R
import kotlinx.android.synthetic.main.appbar.*

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        buttonListener()
    }

    private fun buttonListener() {
        mapHome_btn.setOnClickListener {
            onBackPressed()
        }


    }
}
