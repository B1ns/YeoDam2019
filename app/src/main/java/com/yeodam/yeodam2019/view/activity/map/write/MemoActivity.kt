package com.yeodam.yeodam2019.view.activity.map.write

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yeodam.yeodam2019.R
import kotlinx.android.synthetic.main.activity_memo.*

class MemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)

        buttonListener()
    }

    private fun buttonListener() {
        memo_OK.setOnClickListener {

        }

        memo_Cancle.setOnClickListener {

        }
    }
}
