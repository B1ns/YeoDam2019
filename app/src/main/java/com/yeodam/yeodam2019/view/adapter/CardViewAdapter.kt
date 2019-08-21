package com.yeodam.yeodam2019.view.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.Story
import com.yeodam.yeodam2019.view.activity.main.MainActivity
import com.yeodam.yeodam2019.view.activity.main.loadMapActivity
import org.jetbrains.anko.image
import java.net.URI

class CardViewAdapter(val context: Context, val storyList: ArrayList<Story>) :
    RecyclerView.Adapter<CardViewAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return storyList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(storyList[position], context)
    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardImageView: ImageView = itemView.findViewById(R.id.card_Image)
        val card_detail: ImageView = itemView.findViewById(R.id.card_detail)

        val cardImageCount: TextView = itemView.findViewById(R.id.card_Image_count)
        val cardTitle: TextView = itemView.findViewById(R.id.card_title)
        val cardHashtag: TextView = itemView.findViewById(R.id.card_hashtag)

        fun bind(story: Story, context: Context) {

            Glide.with(context).load(story.image).into(cardImageView)
            cardImageCount.text = story.ImageCount.toString()
            cardTitle.text = story.title
            cardHashtag.text = story.hashtag

            card_detail.setOnClickListener {

            }

            cardImageView.setOnClickListener {

                val intent = Intent(context , loadMapActivity::class.java)
                intent.putExtra("index", story.index)
                ContextCompat.startActivity(context, intent, Bundle())
            }
        }
    }
}