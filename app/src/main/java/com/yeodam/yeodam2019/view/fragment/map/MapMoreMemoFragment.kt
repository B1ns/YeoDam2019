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

        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = mAdapter

        val extra = this.arguments

        val memo = extra?.getStringArrayList("Memo")
        val memoLocation = extra?.getParcelableArrayList<LatLng>("MemoLocation")

        Log.d("memoLoaction", "$memo, s , $memoLocation")

        mAdapter.notifyItemInserted(mAdapter.itemCount + 1)

        return v
    }

}
