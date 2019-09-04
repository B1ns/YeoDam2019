package com.yeodam.yeodam2019.view.adapter.mapMoreAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.MapMoreMemo

class MemoViewAdapter(val context: FragmentActivity?, val mapMoreMemo: ArrayList<MapMoreMemo>) :
    RecyclerView.Adapter<MemoViewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.memo_more, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return mapMoreMemo.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(mapMoreMemo[position], context)
    }

    inner class Holder(itemView : View): RecyclerView.ViewHolder(itemView){

        val memoTitle : TextView = itemView.findViewById(R.id.more_memo)
        val memoLoaction : TextView = itemView.findViewById(R.id.more_memo_loaction)

        fun bind(mapMoreMemo: MapMoreMemo, context: FragmentActivity?){

            memoTitle.text = mapMoreMemo.Memo
            memoLoaction.text = mapMoreMemo.MemoLoaction

        }
    }
}