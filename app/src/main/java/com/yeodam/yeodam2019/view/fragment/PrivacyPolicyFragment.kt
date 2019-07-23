package com.yeodam.yeodam2019.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yeodam.yeodam2019.R


class PrivacyPolicyFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = PrivacyPolicyFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_privacy_policy, container, false)
        return view
    }


}
