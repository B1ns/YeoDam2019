package com.yeodam.yeodam2019.view.activity.map.write

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yeodam.yeodam2019.R
import kotlinx.android.synthetic.main.activity_pay.*

class PayActivity : AppCompatActivity() {

    private var title = "경비"
    private var pay_info = ""
    private var pay_money = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        buttonListener()

    }

    private fun buttonListener() {
        credit_Exit.setOnClickListener {
            onBackPressed()
        }

        credit_upload.setOnClickListener {
            getPayText()
            data()
        }
    }

    private fun data() {
        var intent = Intent()
        intent.putExtra("Pay_Info", pay_info)
        intent.putExtra("Pay_meney", pay_money)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun getPayText() {
        pay_info = pay_info_ET.text.toString()
        pay_money = moneyEditText.text.toString()
    }
}
