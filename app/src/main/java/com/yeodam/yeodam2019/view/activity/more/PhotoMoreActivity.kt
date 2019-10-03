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
import kotlinx.android.synthetic.main.activity_photo_more.*
import java.io.IOException
import java.util.*

class PhotoMoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_more)

        getData()

        photoMoreToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun getData() {
        val intent = intent
        val photo = intent.getParcelableExtra<Bitmap>("Photo")
        val photoLocation = intent.getParcelableExtra<LatLng>("PhotoLocation")

//        val drawable = applicationContext.getDrawable(R.drawable.image_radius)
//        photoMoreImage.background = drawable
//        photoMoreImage.clipToOutline = true

        Glide.with(applicationContext).load(photo).into(photoMoreImage)
        photoLocation_More.text = getCurrentAddress(photoLocation)
    }

    fun getCurrentAddress(latlng: LatLng): String {

        //지오코더... GPS를 주소로 변환
        val geocoder = Geocoder(applicationContext, Locale.getDefault())

        val addresses: List<Address>?

        try {
            addresses = geocoder.getFromLocation(
                latlng.latitude,
                latlng.longitude,
                1
            )
        } catch (ioException: IOException) {
            //네트워크 문제
            Toast.makeText(applicationContext, "서비스 사용불가", Toast.LENGTH_LONG).show()
            return "서비스 사용불가 지역"
        } catch (illegalArgumentException: IllegalArgumentException) {
            Toast.makeText(applicationContext, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show()
            return "잘못된 GPS 좌표"

        }


        if (addresses == null || addresses.isEmpty()) {
            Toast.makeText(applicationContext, "주소 미발견", Toast.LENGTH_LONG).show()
            return "주소 미발견"

        } else {
            val address = addresses[0]
            return address.getAddressLine(0).toString()
        }
    }
}
