package com.B1ns.yeodam2019.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.B1ns.yeodam2019.R
import com.B1ns.yeodam2019.toast
import kotlinx.android.synthetic.main.activity_info.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.textColor

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)


        Button()
        setNickName()
    }

    private fun setNickName() {
        info_btn.setOnClickListener {
            if (nickName.text.toString().isNotEmpty()){
                // pref 설정

                startActivity<MainActivity>()
            }else{
                toast("닉네임을 입력해주세요 !")
            }
        }
    }

    private fun Button() {

        nickName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // 입력이 끝났을 떄 호출
                info_btn.visibility = View.VISIBLE

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 입력 하기전 호출

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 입력되는 텍스트에 변화가 있을 때
                noinfo_btn.visibility = View.GONE
            }

        })
    }


}
