package com.yeodam.yeodam2019.view.activity.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.gdacciaro.iOSDialog.iOSDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.view.activity.splash.OnboardingActivity
import kotlinx.android.synthetic.main.activity_upload.*
import org.jetbrains.anko.startActivity

class UploadActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        buttonLitener()

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
    }
}
