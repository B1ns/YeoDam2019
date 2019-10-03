package com.yeodam.yeodam2019.view.activity.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.gdacciaro.iOSDialog.iOSDialogBuilder
import com.yeodam.yeodam2019.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.ncorti.slidetoact.SlideToActView
import com.yeodam.yeodam2019.YeoDamService
import com.yeodam.yeodam2019.toast
import com.yeodam.yeodam2019.view.activity.main.MainActivity
import com.yeodam.yeodam2019.view.activity.map.write.MemoActivity
import com.yeodam.yeodam2019.view.activity.map.write.PayActivity
import kotlinx.android.synthetic.main.activity_map_activity.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.map_finish_dialog.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton
import java.io.ByteArrayOutputStream
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Suppress(
    "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION",
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)
class MapActivity : AppCompatActivity(), OnMapReadyCallback, Serializable {

    private lateinit var mMap: GoogleMap

    private val REQUEST_ACCESS_FINE_LOCATION = 1000
    private val REQUEST_IMAGE_CAPTURE = 1
    private val CREDIT_CODE = 1111
    private val REQUEST_CODE = 3000

    private val polylineOptions = PolylineOptions().width(10f).color(Color.argb(50, 89, 211, 238))

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: MyLocationCallback

    private lateinit var fab_open: Animation
    private lateinit var fab_close: Animation

    private var start = true
    private var story = true
    private var isFabOpen = false

    var myLatitude: Double = 0.0
    var myLongitude: Double = 0.0

    private var countToday = 0
    private var countLastday = 0

    private var travelStart = ""
    private var travelEnd = ""

    private val locationOne = Location("1")
    private val locationTwo = Location("1")

    private lateinit var location : LatLng

    // 핵심 리스트

    var Map = ArrayList<LatLng>()

    var Memo = ArrayList<String?>()
    var MemoLocation = ArrayList<LatLng>()

    private var Photo = ArrayList<String>()
    private var PhotoLocation = ArrayList<LatLng>()

    private var Pay = ArrayList<String?>()
    private var PayInfo = ArrayList<String?>()
    private var PayLocation = ArrayList<LatLng>()

    private var PhotoUri = ArrayList<Uri>()

    private var PhotoBitmap = ArrayList<Bitmap>()

    // 여기까지
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_activity)

        // 화면이 꺼지지 않게 하기
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        //SupportMapFragment를 가져와서 지도가 준비되면 알림을 받습니다.

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        fab()

        locationInit()

        buttonListener()

        startServiceYeoDam()

        val request = permissionsBuilder(Manifest.permission.CAMERA).build()
        request.send()

    }


    private fun buttonListener() {

        editLayout.setOnClickListener {
            val intent = Intent(this, MemoActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }

        cameraLayout.setOnClickListener {
            camera()
        }

        creditLayout.setOnClickListener {
            val intent = Intent(this, PayActivity::class.java)
            startActivityForResult(intent, CREDIT_CODE)
        }

        mapHome_btn.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            if (this.story) {
                intent.putExtra("onAir", "onAir")
            } else {
                intent.putExtra("onAir", "fail")
            }
            startActivity(intent)
            finish()

        }

        map_menu.setOnClickListener {
            val intent = Intent(this, MapMoreActivity::class.java)
            intent.putParcelableArrayListExtra("Map", Map)
            intent.putStringArrayListExtra("Memo", Memo)
            intent.putParcelableArrayListExtra("MemoLocation", MemoLocation)
            intent.putExtra("Photo", PhotoBitmap)
            intent.putParcelableArrayListExtra("PhotoLocation", PhotoLocation)
            intent.putStringArrayListExtra("Pay", Pay)
            intent.putStringArrayListExtra("PayInfo", PayInfo)
            intent.putParcelableArrayListExtra("PayLocation", PayLocation)
            startActivity(intent)
        }
    }

    private fun camera() {

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun getMarkerBitmapFromView(bitmap: Bitmap?): Bitmap {
        val customMakerView =
            (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.custom_marker,
                null
            )
        val imageView = customMakerView.findViewById<ImageView>(R.id.custom_Image)
        imageView.setImageBitmap(bitmap)

        customMakerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        customMakerView.layout(0, 0, customMakerView.measuredWidth, customMakerView.measuredHeight)
        customMakerView.buildDrawingCache()

        val returnBitmap =
            Bitmap.createBitmap(
                customMakerView.measuredWidth,
                customMakerView.measuredHeight,
                Bitmap.Config.ARGB_8888
            )

        val canvas = Canvas(returnBitmap)
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN)
        val drawble = customMakerView.background
        drawble?.draw(canvas)

        customMakerView.draw(canvas)
        return addImageMarker(returnBitmap)

    }

    /*
    *  Start double floating action button
    */

    private fun fab() {
        fabInit()

        // 각각의 서브 fab

        fab_cancle.setOnClickListener {

            iOSDialogBuilder(this@MapActivity)
                .setTitle("정말 여행을 취소 하시겠습니까?")
                .setSubtitle("여행했던 경로, 메모, 경비, 포함한 모든 정보들은 취소 됩니다.")
                .setBoldPositiveLabel(true)
                .setCancelable(false)
                .setPositiveListener("네") { dialog ->
                    toggleFab()
                    toast("취소됬습니다 !")
                    //여행 취소 로직 작성 구간
                    story = false
                    dialog.dismiss()
                    stopServiceYeoDam()
                    startActivity<MainActivity>()
                    finish()

                }
                .setNegativeListener(
                    getString(R.string.dismiss)
                ) { dialog -> dialog.dismiss() }
                .build().show()

        }


        fab_pause.setOnClickListener {
            toggleFab()
            story = false

            locationTwo.latitude = myLatitude
            locationTwo.longitude = myLongitude

            showFinish()
            // 최상의 fab

        }

        fab_stop.setOnClickListener {
            toggleFab()
            story = false
            fab_Hide()
            map_slider.visibility = View.VISIBLE
            map_slider.onSlideCompleteListener = object : SlideToActView.OnSlideCompleteListener {
                override fun onSlideComplete(view: SlideToActView) {
                    startServiceYeoDam()
                    map_slider.resetSlider()
                    map_slider.visibility = View.GONE
                    story = true
                    fab_Show()
                }
            }
            // 중간의 fab
        }

        // 메인 fab 을 눌렀을시 서브 fab 이 나옴
        fab_main_map.setOnClickListener {
            toggleFab()
        }

        fab_mylocation.setOnClickListener {
            val latLng = LatLng(myLatitude, myLongitude)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

            Log.d("Map", "위도 : $myLatitude, 경도 : $myLongitude")
        }


        fab_N.setOnClickListener {
            val cameraPosition = CameraPosition.Builder()
                .target(LatLng(myLatitude, myLongitude))
                .zoom(17f)
                .bearing(0F)
                .build()

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }

    }

    @SuppressLint("NewApi", "InflateParams")
    private fun showFinish() {

        val current = LocalDateTime.now()
        val endTravel = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val countLast = DateTimeFormatter.ofPattern("dd")
        countLastday = current.format(countLast).toInt()
        travelEnd = current.format(endTravel)

        val meter = locationOne.distanceTo(locationTwo)

        val photoCount = Photo.size.toString()
        val memoCount = Memo.size.toString()
        val creditCount = Pay.size.toString()
        val dayTotal = "$travelStart ~ $travelEnd"


        Log.d("LogTest", "$photoCount + $memoCount")

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.map_finish_dialog, null)
        val photoID = mDialogView.findViewById<TextView>(R.id.dialog_photo)
        val memoID = mDialogView.findViewById<TextView>(R.id.dialog_edit)
        val dayId = mDialogView.findViewById<TextView>(R.id.story_day)
        val payId = mDialogView.findViewById<TextView>(R.id.dialog_credit)
        val mapID = mDialogView.findViewById<ImageView>(R.id.dialog_map)


        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        val callback = GoogleMap.SnapshotReadyCallback {
            mapID.setImageBitmap(it)
        }
        mMap.snapshot(callback)
        photoID.text = photoCount
        memoID.text = memoCount
        dayId.text = dayTotal
        payId.text = creditCount


        val mAlertDialog = mBuilder.show()

        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        mDialogView.dialog_yes.setOnClickListener {
            mAlertDialog.dismiss()

            stopServiceYeoDam()

            val intent = Intent(this, UploadActivity::class.java)
            intent.putParcelableArrayListExtra("Map", Map)
            intent.putStringArrayListExtra("Memo", Memo)
            intent.putParcelableArrayListExtra("MemoLocation", MemoLocation)
            intent.putStringArrayListExtra("Photo", Photo)
            intent.putParcelableArrayListExtra("PhotoLocation", PhotoLocation)
            intent.putStringArrayListExtra("Pay", Pay)
            intent.putStringArrayListExtra("PayInfo", PayInfo)
            intent.putParcelableArrayListExtra("PayLocation", PayLocation)
            intent.putExtra("Day", dayTotal)
            intent.putParcelableArrayListExtra("Uri", PhotoUri)
            intent.putExtra("DayCount", countLastday - countToday)
            intent.putExtra("meter", meter / 1000.toFloat())

            Log.d("uploadCount", "$countToday - $countLastday = ${countToday - countLastday} ")
            Log.d("uploadCount", "${meter / 1000.toFloat()}")
            Log.d("uploadCount", "$meter")

            Log.d("test", Map.toString())
            Log.d("test", Memo.toString())

            startActivity(intent)

            story = false
            fab_Hide()
            map_slider.visibility = View.VISIBLE
            map_slider.onSlideCompleteListener = object : SlideToActView.OnSlideCompleteListener {
                override fun onSlideComplete(view: SlideToActView) {
                    map_slider.resetSlider()
                    map_slider.visibility = View.GONE
                    story = true
                    fab_Show()
                }
            }
        }
        mDialogView.dialog_no.setOnClickListener {
            mAlertDialog.dismiss()

        }

    }

//    private fun showMap() {
//        startActivity<DialogMap>()
//    }

    @SuppressLint("RestrictedApi")
    fun fab_Hide() {
        fab_pause.visibility = View.GONE
        fab_stop.visibility = View.GONE
        fab_main_map.visibility = View.GONE
        fab_cancle.visibility = View.GONE
    }


    @SuppressLint("RestrictedApi")
    fun fab_Show() {
        fab_pause.visibility = View.VISIBLE
        fab_stop.visibility = View.VISIBLE
        fab_main_map.visibility = View.VISIBLE
        fab_cancle.visibility = View.VISIBLE
    }

    @SuppressLint("ResourceType")
    private fun fabInit() {
        fab_open = AnimationUtils.loadAnimation(this, R.animator.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.animator.fab_close)
    }

    private fun toggleFab() {
        if (isFabOpen) {
            fab_main_map.setImageResource(R.drawable.ic_map_air)
            fab_pause.startAnimation(fab_close)
            fab_stop.startAnimation(fab_close)
            fab_cancle.startAnimation(fab_close)
            fab_pause.isClickable = false
            fab_stop.isClickable = false
            fab_cancle.isClickable = false

            isFabOpen = false

        } else {
            fab_main_map.setImageResource(R.drawable.ic_fab_close)
            fab_pause.startAnimation(fab_open)
            fab_stop.startAnimation(fab_open)
            fab_cancle.startAnimation(fab_open)
            fab_pause.isClickable = true
            fab_stop.isClickable = true
            fab_cancle.isClickable = true

            isFabOpen = true
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isMyLocationButtonEnabled = false
        mMap.uiSettings.isCompassEnabled = false

        // Add a marker in Sydney and move the camera
        val korea = LatLng(37.586218, 126.975941)
        mMap.addMarker(MarkerOptions().position(korea).title("대한민국 청와대"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(korea))

        MapsInitializer.initialize(this)

        mMap.setOnMarkerClickListener {
            val intent = Intent(this, MapMoreActivity::class.java)
            intent.putStringArrayListExtra("Memo", Memo)
            intent.putParcelableArrayListExtra("MemoLocation", MemoLocation)
            intent.putExtra("Photo", PhotoBitmap)
            intent.putParcelableArrayListExtra("PhotoLocation", PhotoLocation)
            intent.putStringArrayListExtra("Pay", Pay)
            intent.putStringArrayListExtra("PayInfo", PayInfo)
            intent.putParcelableArrayListExtra("PayLocation", PayLocation)
            startActivity(intent)
            return@setOnMarkerClickListener true
        }

        permissionCheck(cancel = {
            showPermissionInfoDialog()
        }, ok = {
            mMap.isMyLocationEnabled = true
        })
    }

    private fun addImageMarker(bitmap: Bitmap): Bitmap {

        val ImageLatLng = LatLng(myLatitude, myLongitude)

        mMap.addMarker(
            MarkerOptions().position(ImageLatLng).icon(
                BitmapDescriptorFactory.fromBitmap(
                    bitmap
                )
            )
        )

        Photo.add(bitmap.toString())

        PhotoLocation.add(ImageLatLng)

        return bitmap
    }

    private fun locationInit() {
        fusedLocationProviderClient = FusedLocationProviderClient(this)

        locationCallback = MyLocationCallback()

        locationRequest = LocationRequest()
        //GPS 우선
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        // 업데이트 인터벌
        // 위치 정보가 없을때는 업데이트 안 함
        locationRequest.interval = 50000

        // 정확함. 이것보다 짧은 업데이트는 하지 않음
        locationRequest.fastestInterval = 50000

    }

    private fun showPermissionInfoDialog() {
        alert("현재 위치 정보를 얻기 위해서 위치 권한이 필요합니다.", "권한이 필요한 이유") {
            yesButton {
                ActivityCompat.requestPermissions(
                    this@MapActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION
                )
            }

            noButton {
                toast("거절")
            }
        }.show()
    }


    @SuppressLint("MissingPermission")
    private fun addLocationListener() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun removeLocationListener() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    fun startServiceYeoDam() {
        val intent = Intent(this, YeoDamService::class.java)
        ContextCompat.startForegroundService(this, intent)
        addLocationListener()
    }

    fun stopServiceYeoDam() {
        val intent = Intent(this, YeoDamService::class.java)
        stopService(intent)
        removeLocationListener()
    }


    private fun permissionCheck(cancel: () -> Unit, ok: () -> Unit) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                cancel()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION
                )
            }
        } else {
            ok()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_ACCESS_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    addLocationListener()
                } else {
                    //권한 거부
                    toast("권한 거부 됨")
                }
                return
            }
        }
    }

    override fun onBackPressed() {
        if (story) {
            iOSDialogBuilder(this@MapActivity)
                .setTitle("여행중 입니다.")
                .setSubtitle("여행을 종료하시겠습니까?")
                .setBoldPositiveLabel(true)
                .setCancelable(false)
                .setPositiveListener("네") { dialog ->
                    toast("취소됬습니다 !")
                    //여행 취소 로직 작성 구 간
                    toggleFab()
                    dialog.dismiss()
                    finish()
                    startActivity<MainActivity>()
                }
                .setNegativeListener(
                    getString(R.string.dismiss)
                ) { dialog -> dialog.dismiss() }
                .build().show()
        } else {
            startActivity<MainActivity>()
            finish()
        }
    }

    open inner class MyLocationCallback : LocationCallback() {
        @SuppressLint("NewApi")
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation

            location?.run {

                val latLng = LatLng(latitude, longitude)
                val yeodamLatLng: LatLng
                myLatitude = latitude
                myLongitude = longitude

                if (start) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                    val current = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("dd")
                    val startTravel = DateTimeFormatter.ofPattern("yyyy/MM/dd")


                    locationOne.latitude = myLatitude
                    locationOne.longitude = myLongitude

                    travelStart = current.format(startTravel)
                    countToday = current.format(formatter).toInt()
                    start = false
                }



                Log.d("MapsActivity", "위도 : $latitude, 경도 : $longitude")
                if (story) {

                    polylineOptions.add(latLng)

                    //선 그리기
                    mMap.addPolyline(polylineOptions)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

                    // 여행 기록을 담을 객체
                    yeodamLatLng = LatLng(myLatitude, myLongitude)
                    Map.add(yeodamLatLng)

                    Log.d("test", Map.toString())

                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            val memoLatLng = LatLng(myLatitude, myLongitude)
            val memo = data?.getStringExtra("memo")

            val bitmapdraw: BitmapDrawable =
                resources.getDrawable(R.drawable.box_memo) as BitmapDrawable
            val b = bitmapdraw.bitmap
            val smallMarkar = Bitmap.createScaledBitmap(b, 150, 150, false)

            Memo.add(memo)
            MemoLocation.add(memoLatLng)

            mMap.addMarker(
                MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarkar)).position(
                    memoLatLng
                ).title(
                    "메모"
                ).snippet(memo)
            )
        }

        if (requestCode == CREDIT_CODE && resultCode == RESULT_OK) {

            val creditLatLng = LatLng(myLatitude, myLongitude)
            val creditInfo = data?.getStringExtra("Pay_Info")
            val creditMoney = data?.getStringExtra("Pay_meney")

            val bitmapdraw2: BitmapDrawable =
                resources.getDrawable(R.drawable.box_pay) as BitmapDrawable
            val b2 = bitmapdraw2.bitmap
            val smallMarkar2 = Bitmap.createScaledBitmap(b2, 150, 150, false)


            Pay.add(creditInfo)
            PayInfo.add(creditMoney)
            PayLocation.add(creditLatLng)

            mMap.addMarker(
                MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarkar2)).position(
                    creditLatLng
                ).title(
                    creditInfo
                ).snippet(creditMoney)
            )
        }


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            val imageBitmap = data?.extras?.get("data") as Bitmap

            val dataUri = data.data

            PhotoUri.add(dataUri!!)

            PhotoBitmap.add(imageBitmap)

            getMarkerBitmapFromView(imageBitmap)

        }

    }

    override fun onResume() {
        super.onResume()

        // 권한 요청 ⑨
        permissionCheck(cancel = {
            // 위치 정보가 필요한 이유 다이얼로그 표시 ⑩
            showPermissionInfoDialog()
        }, ok = {
            // 현재 위치를 주기적으로 요청 (권한이 필요한 부분) ⑪
            addLocationListener()
        })
    }
}
