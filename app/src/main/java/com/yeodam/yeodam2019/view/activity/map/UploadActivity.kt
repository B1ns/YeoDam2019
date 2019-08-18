package com.yeodam.yeodam2019.view.activity.map

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.gdacciaro.iOSDialog.iOSDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.view.activity.splash.OnboardingActivity
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.android.synthetic.main.activity_upload.*
import org.jetbrains.anko.startActivity

class UploadActivity : AppCompatActivity() {

    private val REQUEST_CODE = 3000

    private val GALLERY_REQUEST_CODE = 1

    private val db = FirebaseFirestore.getInstance()

    private val storyTitle =""
    private val storyCountry = ""
    private val date = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        buttonLitener()

        uploadData()

    }



    private fun buttonLitener() {

        Upload_Upload.setOnClickListener {
            // 업로드

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

    fun setDate(date : String){
        upload_Date.text = date
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            val date = data?.getStringExtra("date")
            if (date != null) {
                setDate(date)
            }
        }

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    var selectedImage = data?.data

                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                    upload_Image.setImageBitmap(bitmap)
                }
            }
        }

    }

    private fun uploadData() {


    }
}
