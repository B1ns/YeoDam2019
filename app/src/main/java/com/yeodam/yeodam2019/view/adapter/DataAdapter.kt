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


class DataAdapter(private val context: Context) : RecyclerView.Adapter<DataAdapter.BaseViewHolder<*>>() {
    override fun getItemCount(): Int {
        return data.size
    }


    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    private var adapterDataList: List<Any> = emptyList()
    private var data : List<Any> = emptyList()


    companion object {
        private const val TYPE_CARDVIEW = 0
        private const val TYPE_LIST = 1
    }

    inner class CardViewHolder(itemView: View) : BaseViewHolder<Story>(itemView) {
        override fun bind(item: Story) {

            var cardImageView: ImageView = itemView.findViewById(R.id.card_Image)
            var cardImageCount: TextView = itemView.findViewById(R.id.card_Image_count)
            var cardTitle: TextView = itemView.findViewById(R.id.card_title)
            var cardHashtag : TextView = itemView.findViewById(R.id.card_hashtag)

        }

    }

    inner class ListViewHolder(itemView: View) : BaseViewHolder<Story>(itemView) {

        override fun bind(item: Story) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_CARDVIEW -> {

                val view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false)
                CardViewHolder(view)

            }
            TYPE_LIST -> {

                val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
                ListViewHolder(view)

            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    //-----------onCreateViewHolder: bind view with data model---------
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = adapterDataList[position]
        when (holder) {
            is CardViewHolder -> holder.bind(element as Story)
            is ListViewHolder -> holder.bind(element as Story)
            else -> throw IllegalArgumentException()
        }
    }

//    override fun getItemViewType(position: Int): Int {
//        return getItem(position).getType()
//    }

}