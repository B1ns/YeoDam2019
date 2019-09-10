package com.yeodam.yeodam2019.view.fragment.map


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.MapMoreMemo
import com.yeodam.yeodam2019.view.adapter.mapMoreAdapter.MemoViewAdapter


open class MapMoreMemoFragment : Fragment() {

    private val MemoStory = arrayListOf<MapMoreMemo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_map_more_memo, container, false)

        val mAdapter = MemoViewAdapter(activity, MemoStory)
        val mLayoutManager = LinearLayoutManager(activity)
        val recyclerView = v.findViewById<RecyclerView>(R.id.map_memo_recyclerView)

        val extra = arguments

        val memo = extra?.getStringArrayList("Memo")
        val memoLocation = extra?.getParcelableArrayList<LatLng>("MemoLocation")

        Log.d("memoLoaction", "$memo, s , $memoLocation")

        var i = 0
        if (memo != null) {
            while (i < memo.size) {
                Log.d("toto", "asdasd")
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

        val memo = extra?.getStringArrayList("Memo")
        val memoLocation = extra?.getParcelableArrayList<LatLng>("MemoLocation")

        Log.d("toto", "$memo, $memoLocation")
        MemoStory.add(MapMoreMemo(memo?.get(i), memoLocation?.get(i)))


    }

}
