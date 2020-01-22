package com.yeodam.yeodam2019.view.activity.main

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gdacciaro.iOSDialog.iOSDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ncorti.slidetoact.SlideToActView
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.YeoDamService
import com.yeodam.yeodam2019.data.Story
import com.yeodam.yeodam2019.data.UserDTO
import com.yeodam.yeodam2019.data.userCount
import com.yeodam.yeodam2019.data.userTitle
import com.yeodam.yeodam2019.toast
import com.yeodam.yeodam2019.view.activity.map.MapActivity
import com.yeodam.yeodam2019.view.activity.setting.ProfileActivity
import com.yeodam.yeodam2019.view.activity.setting.SettingActivity
import com.yeodam.yeodam2019.view.activity.splash.InfoActivity
import com.yeodam.yeodam2019.view.adapter.CardViewAdapter
import com.yeodam.yeodam2019.view.adapter.ListViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private var fab: Boolean = false
    private var itemType: Boolean = true

    private lateinit var userName: String
    private lateinit var userEmail: String
    private lateinit var userPhoto: Uri
    private lateinit var userId: String
    private var recyclerCount: Int = 10
    var story = false

    private var setImage: String? = null
    private var setName: String? = null

    private val db = FirebaseFirestore.getInstance()

    private val YeodamStory = arrayListOf<Story>()

    private var mainTitle: String? = null

    private var Day: Int? = null
    private var Km: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getUserData()

        userInfo()

        buttonListener()

        itemListener()

        addItem()

        setDate()

        updateItem()

        travelData()

        startOnAir()

    }

    private fun startOnAir() {
        if (isMyServiceRunning(YeoDamService::class.java)) {
            fab_main.visibility = View.GONE
            fab_onAir.visibility = View.VISIBLE
        } else {
            fab_main.visibility = View.VISIBLE
            fab_onAir.visibility = View.INVISIBLE
        }

        fab_onAir.setOnClickListener {
            startActivity<MapActivity>()
        }
    }

    private fun travelData() {
        val dbMainData = db.collection("userCount").document("$userName : $userId")
        dbMainData.get().addOnCompleteListener {
            val userTravelData = it.result?.toObject(userCount::class.java)
            if (userTravelData != null) {
                Day = userTravelData.DayCount
                Km = userTravelData.KmCount

                Log.d("DayCountData | ", userTravelData.DayCount.toString())
                Log.d("KmCountData | ", userTravelData.KmCount.toString())

                Log.d("Setting Day | ", Day.toString())
                Log.d("Setting Km | ", Km.toString())
            } else {
                Day = 0
                Km = 0
            }

            count_day.startWith(0)
            count_km.startWith(0)

            count_day.endWith(Day as Number)
            count_km.endWith(Km as Number)

            count_day.start()
            count_km.start()

        }
    }

    @SuppressLint("NewApi", "SetTextI18n")
    private fun setDate() {
        val current = LocalDateTime.now()
        val setYear = DateTimeFormatter.ofPattern("yyyy")
        val setDay = DateTimeFormatter.ofPattern("MM")
        val setYearData = current.format(setYear)
        val setDayData = current.format(setDay)
        main_date.text = "${setYearData}년 ${setDayData}월"
    }


    private fun itemListener() {
        val intent = intent
        val count = intent.getIntExtra("OK", 0)
        if (count == 10) {
            bg.visibility = View.INVISIBLE
        }

    }

    private fun runItem() {


        val cardView: RecyclerView = findViewById(R.id.mainCardView)
        val listView: RecyclerView = findViewById(R.id.mainListView)


        when (recyclerCount) {
            10 -> {
                mainListView.visibility = View.GONE
                mainCardView.visibility = View.VISIBLE

                listView.visibility = View.GONE
                cardView.visibility = View.VISIBLE

                cardView.layoutManager = GridLayoutManager(this, 2)


            }
            20 -> {

                mainCardView.visibility = View.GONE
                mainListView.visibility = View.VISIBLE

                cardView.visibility = View.GONE
                listView.visibility = View.VISIBLE

                listView.layoutManager = LinearLayoutManager(applicationContext)

            }
        }
    }


    private fun getUserData() {

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
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
        }


    }

    private fun userInfo() {

        val docRef = db.collection("userInfo").document("$userName : $userId")

        docRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("USER_INFO_SETTING | ", it.result.toString())
                val userDTO = it.result?.toObject(UserDTO::class.java)
                // Main
                Glide.with(this).load(userDTO?.userImage).into(main_userImage)
                main_userName.text = userDTO?.userName

                setImage = userDTO?.userImage
                setName = userDTO?.userName
            }

            Glide.with(this).load(setImage).into(main_userImage)
            main_userName.text = setName
        }
    }

    @SuppressLint("RestrictedApi")
    private fun buttonListener() {

        fab_main.setOnClickListener {

            slider.visibility = View.VISIBLE

            fab = true

            if (fab) {
                fab_main.visibility = View.INVISIBLE
                sliderListener()
                background()
            }
        }

        mainCardView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    // 아래로 스크롤
                    if (isMyServiceRunning(YeoDamService::class.java)) {
                        Log.d("Service", "ON")
                    } else {
                        fab_main.hide()
                    }
                } else if (dy < 0) {
                    // 위로 스크롤
                    if (isMyServiceRunning(YeoDamService::class.java)) {
                        Log.d("Service", "ON")
                    } else {
                        fab_main.show()
                    }
                }
            }
        })

        mainListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    // 아래로 스크롤
                    if (isMyServiceRunning(YeoDamService::class.java)) {
                        Log.d("Service", "ON")
                    } else {
                        fab_main.hide()
                    }
                } else if (dy < 0) {
                    // 위로 스크롤
                    if (isMyServiceRunning(YeoDamService::class.java)) {
                        Log.d("Service", "ON")
                    } else {
                        fab_main.show()
                    }
                }
            }
        })

        main_userImage_Btn.setOnClickListener {
            startActivity<ProfileActivity>()
        }

        setting_btn.setOnClickListener {
            startActivity<SettingActivity>()
        }

        change_Item.setOnClickListener {

            itemType = if (itemType) {
                change_Item.setBackgroundResource(R.drawable.ic_main_list)
                recyclerCount = 20
                runItem()
                false
            } else {
                change_Item.setBackgroundResource(R.drawable.ic_menu)
                recyclerCount = 10
                runItem()
                true
            }

        }

        userInfoLayout.setOnClickListener {
            startActivity<ProfileActivity>()
        }

        notification_btn.setOnClickListener {
            startActivity<NotificationActivity>()
        }

        main_travel.setOnClickListener {
            toast("아직 준비중인 서비스입니다\n 조금만 기다려주세요~")
        }

    }

    private fun addItem() {

        db.collection("userTitle").document(userName).collection(userId)
            .whereEqualTo("story", "여담")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                for (dc in querySnapshot!!.documentChanges) {
                    val doc = dc.document.toObject(userTitle::class.java)
                    Log.d("dataTest", doc.toString())
                    addYeoDam(doc.title)
                }
            }
    }

    private fun addYeoDam(title: String?) {

        mainTitle = title

        val cardView: RecyclerView = findViewById(R.id.mainCardView)
        val listView: RecyclerView = findViewById(R.id.mainListView)

        val getStory = db.collection("userStory").document("$userName : $userId")
            .collection(mainTitle.toString())
            .document("StoryProfile")
        getStory.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val StoryData = it.result?.toObject(Story::class.java)
                Log.d("testLog", StoryData.toString())
                if (StoryData != null) {

                    YeodamStory.add(StoryData)

                    val mAdapterCard = CardViewAdapter(this, YeodamStory)
                    mAdapterCard.notifyItemInserted(mAdapterCard.itemCount + 1)
                    cardView.adapter = mAdapterCard

                    val mAdapterList = ListViewAdapter(this, YeodamStory)
                    mAdapterList.notifyItemInserted(mAdapterCard.itemCount + 1)
                    listView.adapter = mAdapterList

                    bg.visibility = View.INVISIBLE

                    runItem()

                }
            }
        }
    }


    @SuppressLint("RestrictedApi")
    private fun background() {
        main_background.visibility = View.VISIBLE
        main_background.setBackgroundResource(R.color.background_shadow)

        main_background.setOnClickListener {
            main_background.visibility = View.GONE
            main_background.setBackgroundResource(R.color.background)
            slider.visibility = View.GONE
            fab_main.visibility = View.VISIBLE
            fab = false
        }

    }

    private fun sliderListener() {

        slider.onSlideCompleteListener = object : SlideToActView.OnSlideCompleteListener {
            override fun onSlideComplete(view: SlideToActView) {
                startMap()
                resetSlider()
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun resetSlider() {
        slider.resetSlider()
    }

    @SuppressLint("RestrictedApi")
    private fun startMap() {
        slider.visibility = View.GONE
        main_background.visibility = View.GONE
        startActivity<MapActivity>()
        fab_main.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        backSpace()
    }

    private fun backSpace() {
        iOSDialogBuilder(this@MainActivity)
            .setTitle("여담 : 여행을 담다")
            .setSubtitle("종료하시겠습니까?")
            .setBoldPositiveLabel(true)
            .setCancelable(false)
            .setPositiveListener("네") { dialog ->
                dialog.dismiss()
                finishAffinity()
            }
            .setNegativeListener(
                getString(R.string.dismiss)
            ) { dialog -> dialog.dismiss() }
            .build().show()
    }

    override fun onStart() {
        super.onStart()
        userInfo()
    }

    private fun updateItem() {
        val intent = intent
        val title = intent.getStringExtra("title")
        mainTitle = title
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }

        return false
    }
}

