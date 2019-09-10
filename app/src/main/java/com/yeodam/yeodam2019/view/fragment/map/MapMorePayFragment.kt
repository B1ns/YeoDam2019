package com.yeodam.yeodam2019.view.fragment.map


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.MapMorePay
import com.yeodam.yeodam2019.view.adapter.mapMoreAdapter.PayViewAdapter

open class MapMorePayFragment : Fragment() {

    private val PayStory = arrayListOf<MapMorePay>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_map_more_pay, container, false)

        val mAdapter = PayViewAdapter(activity, PayStory)
        val mLayoutManager = LinearLayoutManager(activity)
        val recyclerView = v.findViewById<RecyclerView>(R.id.map_pay_recyclerView)

        val extra = arguments

        val Pay = extra?.getStringArrayList("Pay")
        val PayInfo = extra?.getStringArrayList("PayInfo")
        val PayLocation = extra?.getParcelableArrayList<LatLng>("PayLocation")

        var i = 0
        if (Pay != null){
            while(i < Pay.size){
                addItem(i)
                i++
            }
        }

        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = mAdapter

        return v
    }

    private fun addItem(i: Int) {

        val extra = arguments

        val Pay = extra?.getStringArrayList("Pay")
        val PayInfo = extra?.getStringArrayList("PayInfo")
        val PayLocation = extra?.getParcelableArrayList<LatLng>("PayLocation")

        PayStory.add(MapMorePay(Pay?.get(i), PayInfo?.get(i), PayLocation?.get(i)))

    }


}
