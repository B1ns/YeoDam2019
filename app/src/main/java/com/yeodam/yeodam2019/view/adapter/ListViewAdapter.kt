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

        fun bind(story: Story, context: Context) {

            listImageView.setImageURI(story.image)
            listImageCount.text = story.ImageCount.toString()
            listTitle.text = story.title
            listHashtag.text = story.hashtag
        }
    }
}