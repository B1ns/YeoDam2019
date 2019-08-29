package com.yeodam.yeodam2019.view.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yeodam.yeodam2019.view.fragment.map.MapMoreAllFragment
import com.yeodam.yeodam2019.view.fragment.map.MapMoreMemoFragment
import com.yeodam.yeodam2019.view.fragment.map.MapMorePayFragment
import com.yeodam.yeodam2019.view.fragment.map.MapMorePhotoFragment

class MapMoreAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragmentTitleList = mutableListOf("전체", "사진", "메모", "경비")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MapMoreAllFragment()

            1 -> MapMorePhotoFragment()

            2 -> MapMoreMemoFragment()

            3 -> MapMorePayFragment()

            else -> MapMoreAllFragment()
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