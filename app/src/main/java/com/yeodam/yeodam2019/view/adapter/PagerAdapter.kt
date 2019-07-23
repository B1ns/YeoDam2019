package com.yeodam.yeodam2019.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

@Suppress("DEPRECATION")
class PagerAdapter(fm : FragmentManager?, tabCount : Int) : FragmentStatePagerAdapter(fm!!){

    private var tabCount: Int = 0

    override fun getItem(position: Int): Fragment {
        return when (position){
            
        }
    }

    override fun getCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}