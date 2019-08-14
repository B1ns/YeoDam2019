package com.yeodam.yeodam2019.view.activity.main

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ncorti.slidetoact.SlideToActView
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.UserDTO
import com.yeodam.yeodam2019.view.activity.map.MapActivity
import com.yeodam.yeodam2019.view.activity.setting.ProfileActivity
import com.yeodam.yeodam2019.view.activity.setting.SettingActivity
import com.yeodam.yeodam2019.view.activity.splash.InfoActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {

    private var fab: Boolean = false
    private var itemType: Boolean = true

    private lateinit var userName: String
    private lateinit var userEmail: String
    private lateinit var userPhoto: Uri
    private lateinit var userId: String

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        info()
        getUserData()
        userInfo()
        buttonListener()
    }

    private fun info() {
        if (!InfoActivity().info) {
            startActivity<InfoActivity>()
        } else {
            toast("여담 : 여행을 담다.")
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
                false
            } else {
                change_Item.setBackgroundResource(R.drawable.ic_menu)
                true
            }
        }

        userInfoLayout.setOnClickListener {
            startActivity<ProfileActivity>()
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

}

