package com.yeodam.yeodam2019.view.activity.splash

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import com.mindorks.editdrawabletext.DrawablePosition
import com.mindorks.editdrawabletext.OnDrawableClickListener
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.toast
import com.yeodam.yeodam2019.view.activity.main.MainActivity
import kotlinx.android.synthetic.main.activity_info.*
import org.jetbrains.anko.startActivity

class InfoActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

//    private val PICK_IMAGE_REQUEST = 71
//    private var filePath: Uri? = null
//    private var firebaseStore: FirebaseStorage? = null
//    private var storageReference: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        butListener()
        setNickName()
        uploadProfile()

    }

    private fun uploadProfile() {

        val user = HashMap<String, Any>()
        user["name"] = nickName.text.toString()
//        user["Image"] =

    }


    private fun setNickName() {
        info_btn.setOnClickListener {
            if (nickName.text.toString().isNotEmpty()) {
                // pref 설정
                finish()
                startActivity<MainActivity>()
            }
        }
    }

    private fun butListener() {

        nickName.setDrawableClickListener(object : OnDrawableClickListener{
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


}
