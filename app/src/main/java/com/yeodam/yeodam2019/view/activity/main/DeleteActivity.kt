package com.yeodam.yeodam2019.view.activity.main

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.yeodam.yeodam2019.R
import kotlinx.android.synthetic.main.activity_delete.*

class DeleteActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var userName: String
    private lateinit var userEmail: String
    private lateinit var userPhoto: Uri
    private lateinit var userId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        buttonListener()
    }

    private fun buttonListener() {
        delete_backspace.setOnClickListener {
            onBackPressed()
        }

        delete_Upload.setOnClickListener {
            updateStoryProfile()
        }
    }

    private fun updateStoryProfile() {
        val intent = intent
        val title = intent.getStringExtra("asd")



    }
}
