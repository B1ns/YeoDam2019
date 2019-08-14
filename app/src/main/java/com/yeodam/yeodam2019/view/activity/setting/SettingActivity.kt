package com.yeodam.yeodam2019.view.activity.setting

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.UserDTO
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.startActivity

class SettingActivity : AppCompatActivity() {

    private lateinit var userName: String
    private lateinit var userEmail: String
    private lateinit var userPhoto: Uri
    private lateinit var userId: String
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        buttonListener()
        getUserData()
        userInfo()

    }

    private fun buttonListener() {
        setting_toolbar.setNavigationOnClickListener {
            finish()
        }

        setting_Info.setOnClickListener {
            startActivity<ProfileActivity>()
        }

        service.setOnClickListener {
            startActivity<ServiceActivity>()
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
                // Setting
                Glide.with(applicationContext).load(userDTO?.userImage).into(setting_Image)
                setting_Name.text = userDTO?.userName
            }
        }
    }
}
