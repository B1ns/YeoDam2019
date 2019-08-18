package com.yeodam.yeodam2019.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.Story

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
        val cardImageCount: TextView = itemView.findViewById(R.id.card_Image_count)
        val cardTitle: TextView = itemView.findViewById(R.id.card_title)
        val cardHashtag: TextView = itemView.findViewById(R.id.card_hashtag)

        fun bind(story: Story, context: Context) {

            cardImageView.setImageURI(story.image)
            cardImageCount.text = story.ImageCount.toString()
            cardTitle.text = story.title
            cardHashtag.text = story.hashtag
        }
    }
}