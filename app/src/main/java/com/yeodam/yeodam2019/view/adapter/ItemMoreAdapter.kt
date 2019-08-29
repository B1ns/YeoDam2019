package com.yeodam.yeodam2019.view.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yeodam.yeodam2019.view.fragment.load.ItemMoreAllFragment
import com.yeodam.yeodam2019.view.fragment.load.ItemMoreMemoFragment
import com.yeodam.yeodam2019.view.fragment.load.ItemMorePayFragment
import com.yeodam.yeodam2019.view.fragment.load.ItemMorePhotoFragment

class ItemMoreAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragmentTitleList = mutableListOf("전체", "사진", "메모", "경비")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ItemMoreAllFragment()
            1 -> ItemMorePhotoFragment()
            2 -> ItemMoreMemoFragment()
            3 -> ItemMorePayFragment()
            else -> ItemMoreAllFragment()
        }

    }

    override fun getCount() = 4


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        //Log.e("FragmentPagerAdapter", "destroyItem position : $position")
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }

}