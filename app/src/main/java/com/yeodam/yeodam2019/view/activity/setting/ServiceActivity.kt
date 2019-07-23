package com.yeodam.yeodam2019.view.activity.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yeodam.yeodam2019.R
import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
    }

    private fun tabListener(){
        serviceTab.addTab(serviceTab.newTab().setText("서비스 약관"))
        serviceTab.addTab(serviceTab.newTab().setText("개인정보 보호 정책"))

        
    }
}
