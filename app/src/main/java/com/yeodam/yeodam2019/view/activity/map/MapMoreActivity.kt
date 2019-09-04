package com.yeodam.yeodam2019.view.activity.map

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.model.LatLng
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.view.activity.main.MainActivity
import com.yeodam.yeodam2019.view.adapter.MapMoreAdapter
import com.yeodam.yeodam2019.view.fragment.map.MapMoreAllFragment
import com.yeodam.yeodam2019.view.fragment.map.MapMoreMemoFragment
import com.yeodam.yeodam2019.view.fragment.map.MapMorePayFragment
import com.yeodam.yeodam2019.view.fragment.map.MapMorePhotoFragment
import kotlinx.android.synthetic.main.activity_map_more.*
import kotlinx.android.synthetic.main.appbar_more.*

class MapMoreActivity : AppCompatActivity() {

    private val adapter by lazy { MapMoreAdapter(supportFragmentManager) }

    private var Memo = ArrayList<String>()
    private var MemoLocation = ArrayList<LatLng>()
    private var Photo = ArrayList<String>()
    private var PhotoLocation = ArrayList<LatLng>()
    private var Pay = ArrayList<String>()
    private var PayInfo = ArrayList<String>()
    private var PayLocation = ArrayList<LatLng>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_more)

        buttonListener()

        mapMoreViewPager.adapter = MapMoreActivity@ adapter

        mapMoreTab.setupWithViewPager(mapMoreViewPager)

        getData()
    }

    private fun getData() {

        val intent = intent

        // Get Data
        Memo = intent.getStringArrayListExtra("Memo")
        MemoLocation = intent.getParcelableArrayListExtra("MemoLocation")
        Photo = intent.getStringArrayListExtra("Photo")
        PhotoLocation = intent.getParcelableArrayListExtra("PhotoLocation")
        Pay = intent.getStringArrayListExtra("Pay")
        PayInfo = intent.getStringArrayListExtra("PayInfo")
        PayLocation = intent.getParcelableArrayListExtra("PayLocation")

        setMain()
        setMemo()
        setPay()
        setPhoto()

    }

    private fun setMain() {
        val fragment = MapMoreAllFragment()
        val bundle = Bundle(7)

        bundle.putStringArrayList("Memo", Memo)
        bundle.putParcelableArrayList("MemoLocation", MemoLocation)

        bundle.putStringArrayList("Pay", Pay)
        bundle.putStringArrayList("PayInfo", PayInfo)
        bundle.putParcelableArrayList("PayLocation", PayLocation)

        bundle.putStringArrayList("Photo", Photo)
        bundle.putParcelableArrayList("PhotoLocation", PhotoLocation)

        fragment.arguments = bundle
    }


    private fun setMemo() {
        val fragment = MapMoreMemoFragment()
        val bundle = Bundle(2)

        bundle.putStringArrayList("Memo", Memo)
        bundle.putParcelableArrayList("MemoLocation", MemoLocation)

        fragment.arguments = bundle
    }

    private fun setPay() {

        val fragment = MapMorePayFragment()
        val bundle = Bundle(3)

        bundle.putStringArrayList("Pay", Pay)
        bundle.putStringArrayList("PayInfo", PayInfo)
        bundle.putParcelableArrayList("PayLocation", PayLocation)

        fragment.arguments = bundle
    }

    private fun setPhoto() {
        val fragment = MapMorePhotoFragment()
        val bundle = Bundle(2)

        bundle.putStringArrayList("Photo", Photo)
        bundle.putParcelableArrayList("PhotoLocation", PhotoLocation)

        fragment.arguments = bundle
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
