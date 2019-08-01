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

        userName.setOnClickListener {

        }

        userDelete.setOnClickListener {
            userDelete()
        }

        profile_toolbar.setOnClickListener {
            onBackPressed()
        }
    }

    fun userDelete() {
        iOSDialogBuilder(this@ProfileActivity)
            .setTitle("정말 탈퇴 하시겠습니까?")
            .setSubtitle("기록했던 앨범의 경로, 메모, 경비, 계정 정보를\n 포함한 모든 정보들은 삭제됩니다.")
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
