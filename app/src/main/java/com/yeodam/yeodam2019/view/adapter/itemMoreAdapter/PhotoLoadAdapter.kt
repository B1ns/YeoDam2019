package com.yeodam.yeodam2019.view.adapter.itemMoreAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.ItemMorePhoto


class PhotoLoadAdapter(val context: Context, val itemMorePhoto: ArrayList<ItemMorePhoto>) :
    RecyclerView.Adapter<PhotoLoadAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.more_photo_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return itemMorePhoto.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemMorePhoto[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemMorePay: ItemMorePhoto, context: Context) {


        }
    }

}