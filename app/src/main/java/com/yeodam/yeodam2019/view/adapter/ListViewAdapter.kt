package com.yeodam.yeodam2019.view.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.Story
import com.yeodam.yeodam2019.view.activity.main.loadMapActivity

class ListViewAdapter(val context: Context, private val storyList: ArrayList<Story>) :
    RecyclerView.Adapter<ListViewAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return storyList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(storyList[position], context)
    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listImageView: ImageView = itemView.findViewById(R.id.list_Image)
        val listImageCount: TextView = itemView.findViewById(R.id.list_count)
        val listHashtag: TextView = itemView.findViewById(R.id.list_hashtag)
        val listTitle: TextView = itemView.findViewById(R.id.list_title)
        val listLayoit : LinearLayout = itemView.findViewById(R.id.list_layout)
        val list_nextPage : LinearLayout = itemView.findViewById(R.id.list_nextPage)

        fun bind(story: Story, context: Context) {

            Glide.with(context).load(story.image).into(listImageView)
            listImageCount.text = story.ImageCount.toString()
            listTitle.text = story.title
            listHashtag.text = story.hashtag

            list_nextPage.setOnClickListener {

            }

            listLayoit.setOnClickListener {
                val intent = Intent(context , loadMapActivity::class.java)
                intent.putExtra("index", story.index)
                ContextCompat.startActivity(context, intent, Bundle())
            }
        }
    }
}