package com.yeodam.yeodam2019.view.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yeodam.yeodam2019.view.fragment.PrivacyPolicyFragment
import com.yeodam.yeodam2019.view.fragment.TermsFragment

class ServiceAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragmentTitleList = mutableListOf("서비스약관", "개인정보 보호 정책")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PrivacyPolicyFragment()

            1 -> TermsFragment()

            else -> PrivacyPolicyFragment()
        }
    }

    override fun getCount() = 2

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        //Log.e("FragmentPagerAdapter", "destroyItem position : $position")
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList[position]
    }

}