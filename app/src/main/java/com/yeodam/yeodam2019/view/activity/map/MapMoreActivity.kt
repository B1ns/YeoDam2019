package com.yeodam.yeodam2019.view.activity.map

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        Photo = intent.getStringArrayListExtra("Photo")
        PhotoLocation = intent.getParcelableArrayListExtra("PhotoLocation")
        Pay = intent.getStringArrayListExtra("Pay")
        PayInfo = intent.getStringArrayListExtra("PayInfo")
        PayLocation = intent.getParcelableArrayListExtra("PayLocation")

        Log.d(
            "bundle",
            "메모 : $Memo, 메모주소 : $MemoLocation, 사진 : $Photo, 사진주소 : $PhotoLocation, 경비 : $Pay, 경비내용 : $PayInfo, 경비주소 : $PayLocation"
        )

        setMain()
        setMemo()
        setPay()
        setPhoto()

    }

    private fun setMain() {
        val fragment = MapMoreAllFragment()
        val bundle = Bundle()

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
        val fragmentMemo = MapMoreMemoFragment()
        val bundleMemo = Bundle()

        bundleMemo.putStringArrayList("Memo", Memo)
        bundleMemo.putParcelableArrayList("MemoLocation", MemoLocation)

        fragmentMemo.arguments = bundleMemo
    }

    private fun setPay() {

        val fragmentPay = MapMorePayFragment()
        val bundlePay = Bundle()

        bundlePay.putStringArrayList("Pay", Pay)
        bundlePay.putStringArrayList("PayInfo", PayInfo)
        bundlePay.putParcelableArrayList("PayLocation", PayLocation)

        fragmentPay.arguments = bundlePay
    }

    private fun setPhoto() {
        val fragmentPhoto = MapMorePhotoFragment()
        val bundlePhoto = Bundle()

        bundlePhoto.putStringArrayList("Photo", Photo)
        bundlePhoto.putParcelableArrayList("PhotoLocation", PhotoLocation)

        fragmentPhoto.arguments = bundlePhoto
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
