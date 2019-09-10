package com.yeodam.yeodam2019.view.activity.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.bumptech.glide.Glide
import com.gdacciaro.iOSDialog.iOSDialogBuilder
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.Story
import com.yeodam.yeodam2019.toast
import com.yeodam.yeodam2019.view.adapter.CardViewAdapter
import com.yeodam.yeodam2019.view.adapter.ListViewAdapter
import kotlinx.android.synthetic.main.activity_delete.*
import org.jetbrains.anko.startActivity

class DeleteActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private var storageReference: StorageReference? = null

    private lateinit var userName: String
    private lateinit var userEmail: String
    private lateinit var userPhoto: Uri
    private lateinit var userId: String

    private var infoTitle: String? = null
    private var infoTravel: String? = null
    private var infoDay: String? = null
    private var infoImage: String? = null

    private var filePath: Uri? = null
    private val GALLERY_REQUEST_CODE = 1

    private var uri: String? = null
    private var imageCount: Int? = null
    private var titleMain: String? = null
    private var hashtag: String? = null
    private var index: Int? = null
    private var day: String? = null

    private lateinit var cardViewAdapter: CardViewAdapter
    private lateinit var listViewAdapter: ListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        getUserData()

        buttonListener()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(delete_title_editText.windowToken, 0)
        return true
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

            getStoryProfile()
        }


    }

    private fun buttonListener() {
        delete_backspace.setOnClickListener {
            onBackPressed()
        }

        delete_Upload.setOnClickListener {
            if (delete_title_editText.text.toString().isNotEmpty() && delete_travel_editText.text.toString().isNotEmpty()) {
                setLoading()
                uploadImage()
            } else {
                toast("수정할 내용을 적어주세요.")
            }
        }

        delete_image_layout.setOnClickListener {
            camera()
        }

        delete_delete.setOnClickListener {
            deleteListener()
        }
    }

    private fun deleteListener() {

        val intent = intent
        val title = intent.getStringExtra("asd")

        iOSDialogBuilder(this@DeleteActivity)
            .setTitle("여행 기록을 삭제 하시겠습니까?")
            .setSubtitle("여행 기록은 삭제되며 복구가 불가능해집니다.")
            .setBoldPositiveLabel(true)
            .setCancelable(false)
            .setPositiveListener("네") { dialog ->
                dialog.dismiss()

                val storage = FirebaseStorage.getInstance()

                val storageRef = storage.reference

                val deserRef =
                    storageRef.child("User_Story/$userName : $userId/$title/StoryTitle/StoryProfile")

                deserRef.delete().addOnSuccessListener {
                }

                val dbStoryProfile =
                    db.collection("userStory").document("$userName : $userId")
                        .collection(title)
                        .document("StoryProfile")
                dbStoryProfile.delete()

                val dbStoryData =
                    db.collection("userStory").document("$userName : $userId").collection(title)
                        .document("StoryData")
                dbStoryData.delete()

                startActivity<MainActivity>()
                toast("삭제 완료")

            }
            .setNegativeListener(
                getString(R.string.dismiss)
            ) { dialog -> dialog.dismiss() }
            .build().show()
    }

    private fun updateStoryProfile(uriString: String) {
        val intent = intent
        val title = intent.getStringExtra("asd")

        titleMain = delete_title_editText.text.toString()
        hashtag = delete_travel_editText.text.toString()

        if (titleMain != null) {
            val dbStoryProfile =
                db.collection("userStory").document("$userName : $userId")
                    .collection(title)
                    .document("StoryProfile")

            dbStoryProfile.set(Story(title, uriString, imageCount, titleMain, hashtag, index, day))
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("b1ns", "OKOKOK")
                        toast("수정 성공!")
                        finishLoading()

                        startActivity<MainActivity>()
                        getStoryProfile()
                    }
                }
                .addOnFailureListener {
                    Log.d("b1ns", it.toString())
                }


        }
    }

    private fun getStoryProfile() {
        val intent = intent
        val title = intent.getStringExtra("asd")

        val dbStoryProfile =
            db.collection("userStory").document("$userName : $userId")
                .collection(title)
                .document("StoryProfile")

        dbStoryProfile.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val getProfile = it.result?.toObject(Story::class.java)
                if (getProfile != null) {
                    infoDay = getProfile.day
                    infoTitle = getProfile.title
                    infoTravel = getProfile.hashtag
                    infoImage = getProfile.image

                    imageCount = getProfile.ImageCount
                    titleMain = getProfile.title
                    hashtag = getProfile.hashtag
                    index = getProfile.index
                    day = getProfile.day

                }

                Glide.with(applicationContext).load(infoImage).into(delete_Image)
                delete_title_editText.hint = infoTitle
                delete_travel_editText.hint = infoTravel
                delete_Date.text = infoDay

            }
        }
    }

    private fun camera() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        val mimeTypes = arrayListOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)

        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun uploadImage() {
        if (filePath != null) {

            val intent = intent
            val title = intent.getStringExtra("asd")

            val storage = FirebaseStorage.getInstance()

            val storageRef = storage.reference

            val deserRef =
                storageRef.child("User_Story/$userName : $userId/$title/StoryTitle/StoryProfile")
            deserRef.delete().addOnSuccessListener {
            }

            val ref =
                storageRef.child("User_Story/$userName : $userId/$title/StoryTitle/StoryProfile")
            val uploadTask = ref.putFile(filePath!!)

            val urlTask =
                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation ref.downloadUrl
                }).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        updateStoryProfile(downloadUri.toString())
                    } else {
                        // Handle 실패할 경우
                    }
                }.addOnFailureListener {
                }
        } else {
            val intent = intent
            val asd = intent.getStringExtra("image")
            updateStoryProfile(asd)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {

                    if (data != null) {
                        filePath = data.data
                        Log.d("file", filePath.toString())
                    }

                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                    delete_Image.setImageBitmap(bitmap)

                    Log.d("image", filePath.toString())
                }
            }
        }

    }

    private fun setLoading() {

        delete_layout.visibility = View.VISIBLE

        delete_bg.visibility = View.VISIBLE
        delete_bg.setBackgroundResource(R.color.background_shadow)

        delete_progress.visibility = View.VISIBLE

    }

    private fun finishLoading() {

        delete_layout.visibility = View.GONE

        delete_bg.visibility = View.GONE

        delete_progress.visibility = View.GONE

    }
}
