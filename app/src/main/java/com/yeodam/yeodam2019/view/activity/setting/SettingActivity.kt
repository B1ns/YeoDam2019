package com.yeodam.yeodam2019.view.activity.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yeodam.yeodam2019.R
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.startActivity

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setting_toolbar.setNavigationOnClickListener {
            finish()
        }

        setting_Info.setOnClickListener {
            startActivity<ProfileActivity>()
        }
    }
}
