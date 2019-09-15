package com.yeodam.yeodam2019.view.activity.map

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.view.activity.main.ItemMoreActivity
import com.yeodam.yeodam2019.view.activity.main.MainActivity
import com.yeodam.yeodam2019.view.adapter.ItemMoreAdapter
import com.yeodam.yeodam2019.view.adapter.MapMoreAdapter
import com.yeodam.yeodam2019.view.fragment.map.MapMoreAllFragment
import com.yeodam.yeodam2019.view.fragment.map.MapMoreMemoFragment
import com.yeodam.yeodam2019.view.fragment.map.MapMorePayFragment
import com.yeodam.yeodam2019.view.fragment.map.MapMorePhotoFragment
import kotlinx.android.synthetic.main.activity_map_more.*
import kotlinx.android.synthetic.main.appbar_more.*

class MapMoreActivity : AppCompatActivity() {

    private val adapter by lazy {
        MapMoreAdapter(
            supportFragmentManager,
            Memo,
            MemoLocation,
            Photo,
            PhotoLocation,
            Pay,
            PayInfo,
            PayLocation
        )
    }

    private var Memo = ArrayList<String>()
    private var MemoLocation = ArrayList<LatLng>()
    private var Photo = ArrayList<Bitmap>()
    private var PhotoLocation = ArrayList<LatLng>()
    private var Pay = ArrayList<String>()
    private var PayInfo = ArrayList<String>()
    private var PayLocation = ArrayList<LatLng>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_more)

        getData()

        buttonListener()

        mapMoreViewPager.adapter = MapMoreActivity@ adapter

        mapMoreTab.setupWithViewPager(mapMoreViewPager)

    }

    private fun getData() {

        val intent = intent

        // Get Data
        Memo = intent.getStringArrayListExtra("Memo")
        MemoLocation = intent.getParcelableArrayListExtra("MemoLocation")
        Photo = intent.getParcelableArrayListExtra("Photo")
        PhotoLocation = intent.getParcelableArrayListExtra("PhotoLocation")
        Pay = intent.getStringArrayListExtra("Pay")
        PayInfo = intent.getStringArrayListExtra("PayInfo")
        PayLocation = intent.getParcelableArrayListExtra("PayLocation")

        Log.d(
            "bundle",
            "메모 : $Memo, 메모주소 : $MemoLocation, 사진 : $Photo, 사진주소 : $PhotoLocation, 경비 : $Pay, 경비내용 : $PayInfo, 경비주소 : $PayLocation"
        )
    }


    private fun buttonListener() {
        more_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("onAir", "onAir")
            startActivity(intent)
        }

        more_map.setOnClickListener {
            onBackPressed()
        }
    }
}
