package com.yeodam.yeodam2019.view.activity.map.write

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.toast
import kotlinx.android.synthetic.main.activity_pay.*

class PayActivity : AppCompatActivity() {

    private var title = "경비"
    private var pay_info = ""
    private var pay_money = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        buttonListener()

        spinnerListener()

        touch()
    }

    fun touch() {

        food()

        sleep()

        bus()

        museum()

        shopping()

        mobile()

        guide()

        etc()

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(moneyEditText.windowToken, 0)
        return true
    }

    private fun spinnerListener() {

        pay_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        moneyEditText.setCurrency("￦")
                    }
                    1 -> {
                        moneyEditText.setCurrency("\$")
                    }

                    2 -> {
                        moneyEditText.setCurrency("€")
                    }

                    3 -> {
                        moneyEditText.setCurrency("¥")
                    }
                    4 -> {
                        moneyEditText.setCurrency("£")
                    }
                }
            }

        }

    }

    private fun buttonListener() {
        credit_Exit.setOnClickListener {
            onBackPressed()
        }

        credit_upload.setOnClickListener {
            if (moneyEditText.text.toString().isNotEmpty()) {
                getPayText()
                data()
            }else{
                toast("내용을 입력해주세요.")
            }
        }
        pay_info_ET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // 입력되는 텍스트에 변화가 있을 때
                if (pay_info_ET.text.toString().isNotEmpty()) {
                    credit_upload.visibility = View.VISIBLE
                } else {
                    credit_upload.visibility = View.GONE
                    credit_not.visibility = View.VISIBLE
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 입력 하기전 호출
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 입력이 끝났을 떄 호출
                credit_not.visibility = View.GONE
            }

        })
    }

    private fun data() {
        val intent = Intent()
        intent.putExtra("Pay_Info", pay_info)
        intent.putExtra("Pay_meney", pay_money)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun getPayText() {
        pay_info = pay_info_ET.text.toString()
        pay_money = moneyEditText.text.toString()
    }

    /*
    여기부터 카테고리 UI 코드입니다
     */

    private fun food() {

        var food = false

        pay_food.setOnClickListener {
            food = if (!food) {
                pay_food.setBackgroundResource(R.drawable.ic_btn_food_on)
                true
            } else {
                pay_food.setBackgroundResource(R.drawable.ic_btn_food_off)
                false
            }
        }
    }

    private fun sleep() {

        var sleep = false

        pay_sleep.setOnClickListener {
            sleep = if (!sleep) {
                pay_sleep.setBackgroundResource(R.drawable.ic_btn_sleep_on)
                true
            } else {
                pay_sleep.setBackgroundResource(R.drawable.ic_btn_sleep_off)
                false
            }
        }
    }

    private fun bus() {

        var bus = false

        pay_bus.setOnClickListener {
            bus = if (!bus) {
                pay_bus.setBackgroundResource(R.drawable.ic_btn_bus_on)
                true
            } else {
                pay_bus.setBackgroundResource(R.drawable.ic_btn_bus_off)
                false
            }
        }
    }

    private fun museum() {
        var museum = false

        pay_museum.setOnClickListener {
            museum = if (!museum) {
                pay_museum.setBackgroundResource(R.drawable.ic_btn_museum_on)
                true
            } else {
                pay_museum.setBackgroundResource(R.drawable.ic_btn_museum_off)
                false
            }
        }
    }

    // 2번째 라인

    private fun shopping() {
        var shopping = false

        pay_shopping.setOnClickListener {
            shopping = if (!shopping) {
                pay_shopping.setBackgroundResource(R.drawable.ic_btn_shopping_on)
                true
            } else {
                pay_shopping.setBackgroundResource(R.drawable.ic_btn_shopping_off)
                false
            }
        }
    }

    private fun mobile() {
        var mobile = false

        pay_mobile.setOnClickListener {
            mobile = if (!mobile) {
                pay_mobile.setBackgroundResource(R.drawable.ic_btn_mobile_on)
                true
            } else {
                pay_mobile.setBackgroundResource(R.drawable.ic_btn_mobile_off)
                false
            }
        }
    }

    private fun guide() {
        var guide = false

        pay_guide.setOnClickListener {
            guide = if (!guide) {
                pay_guide.setBackgroundResource(R.drawable.ic_btn_guide_on)
                true
            } else {
                pay_guide.setBackgroundResource(R.drawable.ic_btn_guide_off)
                false
            }
        }
    }

    private fun etc() {

        var etc = false

        pay_etc.setOnClickListener {
            etc = if (!etc) {
                pay_etc.setBackgroundResource(R.drawable.btn_etc_on)
                true
            } else {
                pay_etc.setBackgroundResource(R.drawable.btn_etc_off)
                false
            }
        }

    }
}
