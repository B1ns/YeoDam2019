package com.yeodam.yeodam2019.view.activity.more

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.view.adapter.PhotoViewAdpater
import kotlinx.android.synthetic.main.activity_photo_more.*
import java.io.ByteArrayOutputStream


class PhotoMoreActivity : AppCompatActivity() {

    private lateinit var adpater: PhotoViewAdpater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_more)

        getData()

        photoMoreBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun getData() {
        val intent = intent
        val photo = intent.getParcelableExtra("Photo") as Bitmap
        val photoLocation = intent.getParcelableExtra<LatLng>("PhotoLocation")

        Glide.with(applicationContext).load(photo).into(photoViewOne)
        adpater = PhotoViewAdpater(this, photo)
//        photoView_Viewpager.adapter = adpater

    }



}
