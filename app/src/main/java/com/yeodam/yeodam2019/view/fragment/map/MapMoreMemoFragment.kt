package com.yeodam.yeodam2019.view.fragment.map


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        getData()

        val mAdapter = MemoViewAdapter(activity, MemoStory)
        val mLayoutManager = LinearLayoutManager(activity)
        val recyclerView = v.findViewById<RecyclerView>(R.id.map_memo_recyclerView)

        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = mAdapter

        mAdapter.notifyItemInserted(mAdapter.itemCount + 1)

        return v
    }

    private fun getData() {

        val bundle = arguments
        val list = bundle?.getStringArrayList("Memo")?.size

        val memo = bundle?.getStringArrayList("Memo")
        val memoLocation = bundle?.getStringArrayList("MemoLocation")

        if (memo != null) {
            Log.d("Log", (memo + ", " + memoLocation!!).toString())
        }

        var i = 0

        if (list != null) {
            while (list > i) {

                val memoData = MapMoreMemo(memo?.get(i), memoLocation?.get(i))
                MemoStory.add(memoData)
                Log.d("Log", MemoStory.toString())
                i++
            }
        }

    }
}
