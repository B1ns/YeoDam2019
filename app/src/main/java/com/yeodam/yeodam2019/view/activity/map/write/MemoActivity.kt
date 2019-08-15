package com.yeodam.yeodam2019.view.activity.map.write

import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.toast
import com.yeodam.yeodam2019.view.activity.map.MapActivity
import kotlinx.android.synthetic.main.activity_memo.*

class MemoActivity : AppCompatActivity() {

    private lateinit var userName: String
    private lateinit var userEmail: String
    private lateinit var userPhoto: Uri
    private lateinit var userId: String
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)

        buttonListener()
        getUserData()
    }

    private fun buttonListener() {
//        memo_OK.setOnClickListener {
//            if (memoText.text.toString().isNotEmpty()) {
//                var latitude = MapActivity().myLatitude
//                var longitude = MapActivity().myLongitude
//                var location = Location("2")
//                location.latitude = latitude
//                location.longitude = longitude
//
//                var memo = memoText.text.toString()
//
//                updateMemo(memo, location)
//
//            } else {
//                toast("메모를 입력해주세요 !")
//            }
//        }

        memo_Cancle.setOnClickListener {

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

    private fun updateMemo(memo: String, location: Location) {

        val docRef = db.collection("Story").document("$userName : $userId").collection("").document("")
    }
}
