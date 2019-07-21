package com.yeodam.yeodam2019

import android.content.Context
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.yeodam.yeodam2019.view.activity.main.MainActivity
import org.jetbrains.anko.startActivity

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun postDelayed(delayMillis: Long, task: () -> Unit) {
    Handler().postDelayed(task, delayMillis)
}

fun View.invisivle(){
    visibility = View.INVISIBLE
}

fun AppCompatActivity.toolbar(toolbar: Toolbar, titleStr: String = "") {
    setSupportActionBar(toolbar)
    supportActionBar?.title = titleStr
    toolbar.apply {
        setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        setNavigationOnClickListener { startActivity<MainActivity>() }
    }
}
