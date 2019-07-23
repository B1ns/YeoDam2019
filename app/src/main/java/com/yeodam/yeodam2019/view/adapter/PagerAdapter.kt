package com.yeodam.yeodam2019.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yeodam.yeodam2019.view.fragment.PrivacyPolicyFragment
import com.yeodam.yeodam2019.view.fragment.TermsFragment

class PagerAdapter(fm : FragmentManager?, tabCount : Int) : FragmentStatePagerAdapter(fm!!){

    private var tabCount: Int = 2

    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> TermsFragment.newInstance()
            1 -> PrivacyPolicyFragment.newInstance()
            else -> null
        }!!
    }

    override fun getCount(): Int {
        return tabCount
    }

}