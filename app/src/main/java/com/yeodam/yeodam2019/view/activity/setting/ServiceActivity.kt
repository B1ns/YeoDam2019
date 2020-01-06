package com.yeodam.yeodam2019.view.activity.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.view.adapter.MapMoreAdapter
import com.yeodam.yeodam2019.view.adapter.ServiceAdapter
import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity : AppCompatActivity() {

    private val adapter by lazy { ServiceAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        serviceViewpager.adapter = ServiceActivity@ adapter
        serviceTab.setupWithViewPager(serviceViewpager)

        setting_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

}
