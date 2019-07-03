package com.yeodam.yeodam2019.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.mindorks.editdrawabletext.DrawablePosition
import com.mindorks.editdrawabletext.OnDrawableClickListener
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.toast
import kotlinx.android.synthetic.main.activity_info.*
import org.jetbrains.anko.startActivity

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        Button()
        setNickName()

    }

    private fun setNickName() {
        info_btn.setOnClickListener {
            if (nickName.text.toString().isNotEmpty()) {
                // pref 설정

                startActivity<MainActivity>()
            }
        }
    }

    private fun Button() {

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
