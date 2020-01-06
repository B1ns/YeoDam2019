package com.yeodam.yeodam2019.view.activity.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.YeoDam
import kotlinx.android.synthetic.main.appbar.*
import org.jetbrains.anko.startActivity

class LoadMapActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var mMap: GoogleMap

    lateinit var userName: String
    lateinit var userEmail: String
    lateinit var userPhoto: Uri
    lateinit var userId: String

    private var Map: ArrayList<LatLng>? = null
    private var Memo: ArrayList<String>? = null
    private var MemoLocation: ArrayList<LatLng>? = null
    private var Photo: ArrayList<String>? = null
    private var PhotoLocation: ArrayList<LatLng>? = null
    private var Pay: ArrayList<String>? = null
    private var PayInfo: ArrayList<String>? = null
    private var PayLocation: ArrayList<LatLng>? = null

    private val polylineOptions = PolylineOptions().width(10f).color(Color.argb(50, 89, 211, 238))
    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_map)

//        getUserData()

        buttonListener()

    }

    private fun buttonListener() {

        mapHome_btn.setOnClickListener {
            onBackPressed()
        }

        map_menu.setOnClickListener {
            startActivity<ItemMoreActivity>()
        }
    }

    private fun getMapdata() {

        val intent = intent
        val index = intent.getStringExtra("asd")

        Log.d("fuck2222", index)

        val getData = db.collection("userStory").document("$userName : $userId").collection(index)
            .document("StoryData")

        getData.get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val userData = it.result?.toObject(YeoDam::class.java)

                    Map = userData?.Map
                    Memo = userData?.Memo
                    MemoLocation = userData?.MemoLocation
                    Photo = userData?.Photo
                    PhotoLocation = userData?.PhotoLocation
                    Pay = userData?.Pay
                    PayInfo = userData?.PayInfo
                    PayLocation = userData?.PayLocation

                }
            }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Map?.get(0), 17f))

        loadLine()
        loadMemo()
        loadPay()
        loadPhoto()

    }


    private fun loadPay() {
        val bitmapdraw: BitmapDrawable = resources.getDrawable(R.drawable.box_pay) as BitmapDrawable
        val b = bitmapdraw.bitmap
        val smallMarkar = Bitmap.createScaledBitmap(b, 125, 125, false)

        val count = Pay?.size

        var i = 0
        if (count != null) {
            while (count > i) {
                mMap.addMarker(
                    MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarkar)).position(PayLocation?.get(i)!!).title(
                        Pay?.get(i)
                    ).snippet(PayInfo?.get(i))
                )
            }
        }
    }

    private fun loadMemo() {
        val bitmapdraw: BitmapDrawable = resources.getDrawable(R.drawable.box_memo) as BitmapDrawable
        val b = bitmapdraw.bitmap
        val smallMarkar = Bitmap.createScaledBitmap(b, 125, 125, false)

        val count = Memo?.size

        var i = 0
        if (count != null) {
            while (count > i) {
                mMap.addMarker(
                    MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarkar)).position(MemoLocation?.get(i)!!).title(
                        "메모"
                    ).snippet(Memo?.get(i))
                )
            }
        }
    }

    private fun loadLine() {

        for(i in this.Map!!){
            polylineOptions.add(i)
        }

    }

    private fun getUserData() {

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified


            val uid = user.uid
            if (name != null) {
                userName = name
            }
            if (email != null) {
                userEmail = email
            }
            if (photoUrl != null) {
                userPhoto = photoUrl
            }
            userId = uid

            getMapdata()
        }

    }


    private fun loadPhoto() {


    }


    private fun getMarkerBitmapFromView(bitmap: String?): Bitmap {
        val customMakerView =
            (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.custom_marker, null)
        val imageView = customMakerView.findViewById<ImageView>(R.id.custom_Image)
//        imageView.setImageBitmap(bitmap)
        Glide.with(applicationContext).load(bitmap).into(imageView)

        customMakerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        customMakerView.layout(0, 0, customMakerView.measuredWidth, customMakerView.measuredHeight)
        customMakerView.buildDrawingCache()

        val returnBitmap =
            Bitmap.createBitmap(customMakerView.measuredWidth, customMakerView.measuredHeight, Bitmap.Config.ARGB_8888)


        val canvas = Canvas(returnBitmap)
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN)
        val drawble = customMakerView.background
        if (drawble != null) {
            drawble.draw(canvas)
        }


        customMakerView.draw(canvas)
        return addImageMarker(returnBitmap)

    }

    private fun addImageMarker(bitmap: Bitmap): Bitmap {

//        val ImageLatLng = LatLng(myLatitude, myLongitude)


//        mMap.addMarker(
//            MarkerOptions().position(ImageLatLng).icon(
//                BitmapDescriptorFactory.fromBitmap(
//                    bitmap
//                )
//            )
//        )
        return bitmap
    }
}
