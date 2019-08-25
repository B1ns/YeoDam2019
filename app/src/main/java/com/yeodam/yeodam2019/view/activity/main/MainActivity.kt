package com.yeodam.yeodam2019.view.activity.main

import android.annotation.SuppressLint
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
import com.yeodam.yeodam2019.data.Story
import com.yeodam.yeodam2019.data.UserDTO
import com.yeodam.yeodam2019.data.userIndex
import com.yeodam.yeodam2019.view.activity.map.MapActivity
import com.yeodam.yeodam2019.view.activity.setting.ProfileActivity
import com.yeodam.yeodam2019.view.activity.setting.SettingActivity
import com.yeodam.yeodam2019.view.activity.splash.InfoActivity
import com.yeodam.yeodam2019.view.adapter.CardViewAdapter
import com.yeodam.yeodam2019.view.adapter.ListViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {

    private var fab: Boolean = false
    private var itemType: Boolean = true

    private lateinit var userName: String
    private lateinit var userEmail: String
    private lateinit var userPhoto: Uri
    private lateinit var userId: String
    private var recyclerCount: Int = 10
    var story = false

    private val db = FirebaseFirestore.getInstance()

    private val YeodamStory = arrayListOf<Story>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        info()

        getUserData()

        userInfo()

        buttonListener()

        refresh()


    }

    private fun runItem() {
        val cardView: RecyclerView = findViewById(R.id.mainCardView)
        val listView: RecyclerView = findViewById(R.id.mainListView)

        bg.visibility = View.GONE

        when (recyclerCount) {
            10 -> {
                mainRecyclerListLayout.visibility = View.GONE
                mainRecyclerCardLayout.visibility = View.VISIBLE

                listView.visibility = View.GONE
                cardView.visibility = View.VISIBLE
                cardView.layoutManager = GridLayoutManager(this, 2)

                val mAdapter = CardViewAdapter(this, YeodamStory)
                cardView.adapter = mAdapter
                mAdapter.notifyDataSetChanged()
            }
            20 -> {

                mainRecyclerCardLayout.visibility = View.GONE
                mainRecyclerListLayout.visibility = View.VISIBLE

                cardView.visibility = View.GONE
                listView.visibility = View.VISIBLE
                listView.layoutManager = LinearLayoutManager(applicationContext)

                val mAdapter = ListViewAdapter(this, YeodamStory)
                listView.adapter = mAdapter
                mAdapter.notifyDataSetChanged()

            }
        }
    }


    private fun info() {
        if (!InfoActivity().info) {
            userInfo()
            startActivity<InfoActivity>()
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

        lottie.playAnimation()

        val docRef = db.collection("userInfo").document("$userName : $userId")

        docRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("asddd", it.result.toString())
                val userDTO = it.result?.toObject(UserDTO::class.java)
                // Main
                Glide.with(this).load(userDTO?.userImage).into(main_userImage)
                main_userName.text = userDTO?.userName
            }
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

    }

    private fun addItem() {

        var counter: Int? = null

        val docRef = db.collection("userIndex").document("$userName : $userId")
        docRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val countData = it.result?.toObject(userIndex::class.java)
                Log.d("testLog", countData.toString())
                counter = countData?.index

                addStory(counter)
            }
        }

    }

    private fun addStory(index: Int?) {

        var indexCount = index

        if (indexCount != null) {
            while (indexCount > 0) {

                val getStory = db.collection("userStory").document("$userName : $userId").collection(index.toString())
                    .document("StoryProfile")
                getStory.get().addOnCompleteListener {
                    val Story = it.result?.toObject(Story::class.java)
                    Log.d("testLog", Story.toString())
                    if (Story != null) {
                        Log.d("testLog", indexCount.toString())
                        YeodamStory.add(Story)
                        runItem()
                    }
                }

                indexCount--
            }
        }
    }

    private fun refresh() {

        addItem()

        mainRecyclerCardLayout.setOnRefreshListener {
            runItem()
            mainRecyclerCardLayout.isRefreshing = false
        }

        mainRecyclerListLayout.setOnRefreshListener {
            runItem()
            mainRecyclerListLayout.isRefreshing = false
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

    fun backSpace() {
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



}

