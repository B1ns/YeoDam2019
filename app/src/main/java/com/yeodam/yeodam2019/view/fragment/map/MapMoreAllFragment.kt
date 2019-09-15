package com.yeodam.yeodam2019.view.fragment.map


import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.*
import com.yeodam.yeodam2019.view.adapter.mapMoreAdapter.AllViewAdapter

open class MapMoreAllFragment : Fragment() {

    private var adapterDataList = ArrayList<Any>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_map_more_all, container, false)

        val mAdapter = AllViewAdapter(activity, adapterDataList)

        val mLayoutManager = GridLayoutManager(context, 3)
        val recyclerView = v.findViewById<RecyclerView>(R.id.map_all_recyclerView)

        val extra = arguments

        val photo = extra?.getParcelableArrayList<Bitmap>("Photo")
        val memo = extra?.getStringArrayList("Memo")
        val Pay = extra?.getStringArrayList("Pay")

        var photoCount = 0
        var memoCount = 0
        var payCount = 0

        if (photo != null && photo.size > 0) {

            Log.d("AKA", "OK1")

            while (photoCount < photo.size) {
                addPhotoItem(photoCount)
                photoCount++
            }
        }

        if (memo != null && memo.size > 0) {
            Log.d("AKA", "OK2")
            while (memoCount < memo.size) {
                addMemoItem(memoCount)
                memoCount++
            }
        }


        if (Pay != null && Pay.size > 0) {
            Log.d("AKA", "OK3")
            while (payCount < Pay.size) {
                addPayItem(payCount)
                payCount++
            }
        }

        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = mAdapter

        return v
    }

    private fun addPayItem(payCount: Int) {
        val extra = arguments

        val Pay = extra?.getStringArrayList("Pay")
        val PayInfo = extra?.getStringArrayList("PayInfo")

        adapterDataList.add(
            Pay(Pay?.get(payCount), PayInfo?.get(payCount))
        )
    }

    private fun addMemoItem(memoCount: Int) {
        val extra = arguments

        val memo = extra?.getStringArrayList("Memo")

        adapterDataList.add(Memo(memo?.get(memoCount)))
    }

    private fun addPhotoItem(photoCount: Int) {
        val extra = arguments

        val photo = extra?.getParcelableArrayList<Bitmap>("Photo")

        adapterDataList.add(Photo(photo?.get(photoCount)))
    }
}
