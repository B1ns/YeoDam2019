package com.yeodam.yeodam2019.view.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.yeodam.yeodam2019.R


class PhotoViewAdpater(val context: Context, bitmap: Bitmap) : PagerAdapter() {

    private val images = bitmap

    private lateinit var inflater: LayoutInflater

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as LinearLayout
    }

    override fun getCount(): Int {
        return 1
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.photoview_slider, container, false)

        val photoView = view?.findViewById(R.id.photoView) as PhotoView

        Glide.with(context).load(images).into(photoView)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.invalidate()
    }

}