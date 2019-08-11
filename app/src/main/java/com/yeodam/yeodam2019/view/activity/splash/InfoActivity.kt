package com.yeodam.yeodam2019.view.activity.splash

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.mindorks.editdrawabletext.DrawablePosition
import com.mindorks.editdrawabletext.OnDrawableClickListener
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.toast
import com.yeodam.yeodam2019.view.activity.main.MainActivity
import kotlinx.android.synthetic.main.activity_info.*
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.collections.HashMap

class InfoActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    lateinit var userName: String
    lateinit var userEmail: String
    lateinit var userPhoto: Uri
    lateinit var userId: String
//    var userName: String? = null
//    var userEmail: String? = null
//    var userPhoto: Uri? = null
//    var userid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        firebaseInit()
        buttonListener()
        setting()
        getUserData()
    }


    private fun firebaseInit() {
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

    }

    fun getUserData() {

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


    private fun uploadProfile(uri: String) {

        val user = HashMap<String, Any>()
        user["name"] = nickName.text.toString()
        user["image"] = uri

        db.collection("userInfo").document(userName)
            .set(user)
            .addOnCompleteListener {
                toast("선택 완료!")
            }
            .addOnCanceledListener {
                toast("다시 한번 시도 해주세요 !")
            }
    }


    private fun setting() {
        info_btn.setOnClickListener {
            if (nickName.text.toString().isNotEmpty()) {
                // pref 설정
                uploadImage()

                finish()
                startActivity<MainActivity>()
            }
        }
    }

    private fun buttonListener() {


        infoImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "사진을 선택해주세요."), PICK_IMAGE_REQUEST)

        }

        /*
        UI
         */
        nickName.setDrawableClickListener(object : OnDrawableClickListener {
            override fun onClick(target: DrawablePosition) {
                nickName.setText("")
            }
        })

        nickName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // 입력이 끝났을 떄 호출

                if (nickName.text.toString().isNotEmpty()) {
                    info_btn.visibility = View.VISIBLE
                } else {
                    info_btn.visibility = View.GONE
                    noinfo_btn.visibility = View.VISIBLE
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 입력 하기전 호출

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 입력되는 텍스트에 변화가 있을 때

                noinfo_btn.visibility = View.GONE

            }

        })

        noinfo_btn.setOnClickListener {
            toast("닉네임을 입력해주세요 !")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            filePath = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
            infoImage.setImageBitmap(bitmap)
        }
    }

    private fun uploadImage() {
        if (filePath != null) {
            val ref = storageReference?.child("User_Image/$userName")
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
                    uploadProfile(downloadUri.toString())
                } else {
                    // Handle 실패할 경우
                }
            }?.addOnFailureListener {
            }
        }

    }
}
