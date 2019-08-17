package com.yeodam.yeodam2019.view.activity.map.write

import android.app.Activity
import android.content.Intent
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
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
    }

    private fun buttonListener() {
        memo_OK.setOnClickListener {
            val intent = Intent()
            intent.putExtra("memo", memoText.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        memo_Cancle.setOnClickListener {
            onBackPressed()
        }
    }

}
