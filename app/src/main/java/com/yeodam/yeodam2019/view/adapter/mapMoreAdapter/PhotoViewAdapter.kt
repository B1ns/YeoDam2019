package com.yeodam.yeodam2019.view.adapter.mapMoreAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.MapMorePhoto

class PhotoViewAdapter(val context: Context, val photo: ArrayList<MapMorePhoto>) :
    RecyclerView.Adapter<PhotoViewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.more_photo_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return photo.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(photo[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val photoImage : ImageView = itemView.findViewById(R.id.more_photo)

        fun bind(photo: MapMorePhoto, context: Context) {

            Glide.with(context).load(photo.Photo).into(photoImage)
        }
    }
}