package com.yeodam.yeodam2019.view.activity.more

import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.view.adapter.PhotoViewAdpater
import kotlinx.android.synthetic.main.activity_photo_more.*
import java.io.IOException
import java.util.*

class PhotoMoreActivity : AppCompatActivity() {

    private lateinit var adpater : PhotoViewAdpater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_more)

        getData()

    }

    private fun getData() {
        val intent = intent
        val photo = intent.getParcelableExtra("Photo") as Bitmap
        val photoLocation = intent.getParcelableExtra<LatLng>("PhotoLocation")

        adpater = PhotoViewAdpater(this, photo)
        photoView_Viewpager.adapter = adpater

    }

}
