package com.yeodam.yeodam2019.view.activity.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yeodam.yeodam2019.R
import android.widget.Toast
import com.gdacciaro.iOSDialog.iOSDialogBuilder
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userDelete.setOnClickListener {
            userDelete()
        }
    }



    fun userDelete() {
        iOSDialogBuilder(this@ProfileActivity)
            .setTitle("회원탈퇴")
            .setSubtitle("정말 탈퇴 하시겠습니까?")
            .setBoldPositiveLabel(true)
            .setCancelable(false)
            .setPositiveListener("네") { dialog ->
                Toast.makeText(this@ProfileActivity, "탈퇴되었습니다 !", Toast.LENGTH_LONG).show()
                //회원 탈퇴 로직 작성 구간
                dialog.dismiss()
            }
            .setNegativeListener(
                getString(R.string.dismiss)
            ) { dialog -> dialog.dismiss() }
            .build().show()
    }
}
