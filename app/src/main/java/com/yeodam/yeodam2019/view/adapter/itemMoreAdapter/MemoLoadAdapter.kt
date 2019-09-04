package com.yeodam.yeodam2019.view.adapter.itemMoreAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.ItemMoreMemo

class MemoLoadAdapter(val context: Context, val itemMoreMemo: ArrayList<ItemMoreMemo>) :
    RecyclerView.Adapter<MemoLoadAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoLoadAdapter.Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.more_memo_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return itemMoreMemo.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemMoreMemo[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemMoreMemo: ItemMoreMemo, context: Context) {

        }
    }
}