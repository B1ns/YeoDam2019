package com.yeodam.yeodam2019.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yeodam.yeodam2019.R

class TermsFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = TermsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_terms, container, false)

        return view
    }


}
