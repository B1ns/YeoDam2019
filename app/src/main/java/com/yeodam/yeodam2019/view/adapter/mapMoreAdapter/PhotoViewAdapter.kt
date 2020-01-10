package com.yeodam.yeodam2019.view.adapter.mapMoreAdapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.MapMorePhoto
import com.yeodam.yeodam2019.view.activity.more.PhotoMoreActivity

class PhotoViewAdapter(val context: FragmentActivity?, val photo: ArrayList<MapMorePhoto>) :
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
        val photoLayout : LinearLayout = itemView.findViewById(R.id.more_photoLayout)

        fun bind(photo: MapMorePhoto, context: FragmentActivity?) {

            Glide.with(context!!).load(photo.Photo).into(photoImage)

            photoLayout.setOnClickListener {
                val intent = Intent(context, PhotoMoreActivity::class.java)
                intent.putExtra("Photo", photo.Photo)
                intent.putExtra("PhotoLocation", photo.PhotoLocation)
                ContextCompat.startActivity(context, intent, Bundle())
            }
        }
    }
}