package com.yeodam.yeodam2019.view.activity.map.write

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.toast
import com.yeodam.yeodam2019.utils.LinedEditText
import com.yeodam.yeodam2019.view.activity.map.MapActivity
import kotlinx.android.synthetic.main.activity_memo.*

class MemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)

        buttonListener()

    }

    private fun buttonListener() {

        memo_Cancle.setOnClickListener {
            onBackPressed()
        }

        memo_OK.setOnClickListener {

            val memoText = "${memoTextOne.text}\n${memoTextTwo.text}\n${memoTextThree.text}"

            if (memoText.isNotEmpty()) {
                val intent = Intent()
                intent.putExtra("memo", memoText)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                toast("메모를 입력해주세요.")
            }
        }

        memoTextOne.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (memoTextOne.text.toString().isNotEmpty()) {
                    memo_OK.visibility = View.VISIBLE
                } else {
                    memo_OK.visibility = View.GONE
                    memo_Not.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                memo_Not.visibility = View.GONE
            }

        })
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(memoTextOne.windowToken, 0)
        return true
    }

}
