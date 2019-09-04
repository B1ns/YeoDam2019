package com.yeodam.yeodam2019.view.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.view.adapter.ItemMoreAdapter
import kotlinx.android.synthetic.main.activity_item_more.*
import kotlinx.android.synthetic.main.appbar_more.*
import org.jetbrains.anko.startActivity

class ItemMoreActivity : AppCompatActivity() {

    private val adapter by lazy { ItemMoreAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_more)

        itemMoreViewPager.adapter = ItemMoreActivity@ adapter

        itemMoreTab.setupWithViewPager(itemMoreViewPager)

        buttonListener()
    }

    private fun buttonListener() {
        more_back.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }

        more_map.setOnClickListener {
            onBackPressed()
        }
    }
}
