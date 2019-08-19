package com.yeodam.yeodam2019.view.activity.map

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.gdacciaro.iOSDialog.iOSDialogBuilder
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.Story
import com.yeodam.yeodam2019.data.YeoDam
import com.yeodam.yeodam2019.view.activity.main.MainActivity
import kotlinx.android.synthetic.main.activity_upload.*
import org.jetbrains.anko.toast

class UploadActivity : AppCompatActivity() {

    private val REQUEST_CODE = 3000

    private val GALLERY_REQUEST_CODE = 1

    private var filePath: Uri? = null

    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    private val db = FirebaseFirestore.getInstance()


    lateinit var userName: String
    lateinit var userEmail: String
    lateinit var userPhoto: Uri
    lateinit var userId: String

    private var index = 0

    private var StoryDay: String? = null
    private var StoryLoaction: LatLng? = null
    private var StoryImage: String? = null

    private var Map = ArrayList<LatLng>()
    private var Memo = ArrayList<String>()
    private var MemoLocation = ArrayList<LatLng>()
    private var Photo = ArrayList<String>()
    private var PhotoLocation = ArrayList<LatLng>()
    private var Pay = ArrayList<String>()
    private var PayInfo = ArrayList<String>()
    private var PayLocation = ArrayList<LatLng>()
    private var Day = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        buttonLitener()

        getUserData()

        firebaseInit()
    }


    private fun Yeodam() {

        val intent = intent

        // Get Data
        Map = intent.getParcelableArrayListExtra("Map")
        Memo = intent.getStringArrayListExtra("Memo")
        MemoLocation = intent.getParcelableArrayListExtra("MemoLocation")
        Photo = intent.getStringArrayListExtra("Photo")
        PhotoLocation = intent.getParcelableArrayListExtra("PhotoLocation")
        Pay = intent.getStringArrayListExtra("Pay")
        PayInfo = intent.getStringArrayListExtra("PayInfo")
        PayLocation = intent.getParcelableArrayListExtra("PayLocation")
        Day = intent.getStringExtra("Day")


    }

    private fun Upload(uri: String) {

        var image = uri
        var ImageCount = Photo.size
        val storyTitle = upload_title_editText.text.toString()
        val storyCountry = upload_travel_editText.text.toString()
        val storyDay = StoryDay
        var YeodamStory = ArrayList<String>()

        index++

        db.collection("userStory").document("$userName : $userId").collection(index.toString()).document("StoryProfile")
            .set(Story(image, ImageCount, storyTitle, storyCountry))
            .addOnCompleteListener {
                YeodamStory.add(storyTitle)
                toast("여담")
            }
            .addOnCanceledListener {
                toast("다시 한번 시도 해주세요 !")
            }

        val db = db.collection("userStory").document("$userName : $userId").collection(index.toString())
            .document("StoryData")
        db.set(YeoDam(Map, Memo, MemoLocation, Photo, PhotoLocation, Pay, PayInfo, PayLocation))
            .addOnCompleteListener {
                val intent = Intent(this, MainActivity::class.java)
                intent.putStringArrayListExtra("title", YeodamStory)
                toast("업로드 성공입니다!")
                startActivity(intent)
                finish()
            }


        var hashIndex = hashMapOf("index" to index)

        db.collection("$userName : $userId").document("count")
        db.set(hashIndex)
            .addOnCompleteListener {

            }
    }


    private fun firebaseInit() {
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

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
        }
    }


    private fun buttonLitener() {

        Upload_Upload.setOnClickListener {
            // 업로드
            Yeodam()
            MainActivity().story = true
            uploadImage()
        }

        Upload_Cancle.setOnClickListener {
            // 취소

            iOSDialogBuilder(this@UploadActivity)
                .setTitle("여행 기록 저장을 취소하시겠습니까?")
                .setSubtitle("여행은 저장되지 않으며, [일시정지] 상태로 전 페이지로 돌아갑니다.")
                .setBoldPositiveLabel(true)
                .setCancelable(false)
                .setPositiveListener("네") { dialog ->
                    dialog.dismiss()
                    finish()
                }
                .setNegativeListener(
                    getString(R.string.dismiss)
                ) { dialog -> dialog.dismiss() }
                .build().show()
        }

        upload_image_layout.setOnClickListener {
            camera()
        }
    }

    private fun camera() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        val mimeTypes = arrayListOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)

        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    var selectedImage = data?.data
                    StoryImage = selectedImage.toString()
                    filePath = selectedImage

                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                    upload_Image.setImageBitmap(bitmap)
                }
            }
        }

    }

    private fun uploadImage() {
        if (filePath != null) {
            var storyTitle = upload_title_editText.text.toString()
            val ref = storageReference?.child("User_Story/$userName : $userId/$storyTitle/StoryTitle/StoryProfile")
            val uploadTask = ref?.putFile(filePath!!)

            val urlTask = uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }

                return@Continuation ref.downloadUrl

            })?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    if (downloadUri != null) {
                        Upload(downloadUri.toString())
                    }
                } else {
                    // Handle 실패할 경우
                }
            }?.addOnFailureListener {
            }
        }

    }

    private fun uploadStory() {
        if (filePath != null) {
            var storyTitle = upload_title_editText.text.toString()
            val ref = storageReference?.child("User_Story/$userName : $userId/$storyTitle/StoryTitle/StoryData")
            val uploadTask = ref?.putFile(filePath!!)

            val urlTask = uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }

                return@Continuation ref.downloadUrl

            })?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
//                    Upload(downloadUri.toString())
                } else {
                    // Handle 실패할 경우
                }
            }?.addOnFailureListener {
            }
        }

    }

}
