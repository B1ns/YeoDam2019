package com.yeodam.yeodam2019.view.activity.setting

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.yeodam.yeodam2019.R
import android.widget.Toast
import com.bumptech.glide.Glide
import com.gdacciaro.iOSDialog.iOSDialogBuilder
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.yeodam.yeodam2019.data.UserDTO
import com.yeodam.yeodam2019.fcm.FcmCheck
import com.yeodam.yeodam2019.toast
import com.yeodam.yeodam2019.view.activity.splash.OnboardingActivity
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.startActivity

class ProfileActivity : AppCompatActivity(){

    private val PICK_IMAGE_REQUEST = 71
    private lateinit var userName: String
    private lateinit var userEmail: String
    private lateinit var userPhoto: Uri
    private lateinit var userId: String
    private var storageReference: StorageReference? = null
    private var filePath: Uri? = null
    private var image = false
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        buttonListener()
        getUserData()
        userInfo()
    }



    private fun buttonListener() {

        profile_Layout.setOnClickListener {
            profile_Name.visibility = View.GONE
            edit_Nickname.visibility = View.VISIBLE
        }

        profile_Image.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(Intent.createChooser(intent, "사진을 선택해주세요."), PICK_IMAGE_REQUEST)
        }

        ok_btn.setOnClickListener {
            if (edit_Nickname.text.toString().isNotEmpty() && image) {
                Log.d("asdddo", "ok")
                uploadImage()
                edit_Nickname.visibility = View.GONE
                profile_Name.visibility = View.VISIBLE
            } else {
                toast("사진을 선택해주시고, 닉네임을 입력해주세요 !")
            }
        }

        userDelete.setOnClickListener {
            userDelete()
        }

        profile_toolbar.setOnClickListener {
            onBackPressed()
        }
    }

    fun userUpdate(uri: String) {

        val userUpdate_profile = mutableMapOf<String, Any>()

        userUpdate_profile["userImage"] = uri
        userUpdate_profile["userName"] = edit_Nickname.text.toString()

        Log.d("okdddd", "ok")

        db.collection("userInfo").document("$userName : $userId").update(userUpdate_profile)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    toast("적용되었습니다.")
                } else {
                    toast("오류")
                }

            }.addOnFailureListener {
                Log.d("asdddo", it.toString())
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
                Glide.with(applicationContext).load(userDTO?.userImage).into(profile_Image)
                profile_Name.text = userDTO?.userName
            }
        }
    }

    fun userDelete() {
        iOSDialogBuilder(this@ProfileActivity)
            .setTitle("정말 탈퇴 하시겠습니까?")
            .setSubtitle("기록했던 앨범의 경로, 메모, 경비, 계정   정보를 포함한 모든 정보들은 삭제됩니다.")
            .setBoldPositiveLabel(true)
            .setCancelable(false)
            .setPositiveListener("네") { dialog ->
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(this@ProfileActivity, "탈퇴되었습니다 !", Toast.LENGTH_LONG).show()
                //회원 탈퇴 로직 작성 구간
                startActivity<OnboardingActivity>()
                dialog.dismiss()
            }
            .setNegativeListener(
                getString(R.string.dismiss)
            ) { dialog -> dialog.dismiss() }
            .build().show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }
            image = true
            filePath = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
            profile_Image.setImageBitmap(bitmap)
        }
    }

    private fun uploadImage() {
        if (filePath != null) {
            val ref = storageReference?.child("User_Image/$userName/$userId")
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
                    userUpdate(downloadUri.toString())
                } else {
                    // Handle 실패할 경우
                    toast("실팽")
                }
            }?.addOnFailureListener {
                toast("errro : $it")
            }
        }
    }
}
