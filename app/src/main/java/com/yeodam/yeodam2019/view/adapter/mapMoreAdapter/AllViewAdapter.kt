package com.yeodam.yeodam2019.view.adapter.mapMoreAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.Memo
import com.yeodam.yeodam2019.data.Pay
import com.yeodam.yeodam2019.data.Photo

class AllViewAdapter(private val context: Context) :
    RecyclerView.Adapter<AllViewAdapter.BaseViewHolder<*>>() {
    override fun getItemCount(): Int = 3

    private var adapterDataList: List<Any> = emptyList()

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    companion object {
        private const val TYPE_PHOTO = 0
        private const val TYPE_MEMO = 1
        private const val TYPE_PAY = 2
    }

    //----------------PhotoViewHolder | FamilyDataModel------------
    inner class PhotoViewHolder(itemView: View) : BaseViewHolder<Photo>(itemView) {

        override fun bind(item: Photo) {
            //Do your view assignment here from the data model
        }
    }

    //----------------MemoViewHolder | FriendDataModel-------------
    inner class MemoViewHolder(itemView: View) : BaseViewHolder<Memo>(itemView) {

        override fun bind(item: Memo) {
            //Do your view assignment here from the data model
        }
    }

    //----------------PayViewHolder | ColleagueDataModel-------
    inner class PayViewHolder(itemView: View) : BaseViewHolder<Pay>(itemView) {

        override fun bind(item: Pay) {
            //Do your view assignment here from the data model
        }
    }

    //--------onCreateViewHolder: inflate layout with view holder-------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_PHOTO -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.more_photo_item, parent, false)
                PhotoViewHolder(view)
            }
            TYPE_MEMO -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.more_memo_item, parent, false)
                MemoViewHolder(view)
            }
            TYPE_PAY -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.more_pay_item, parent, false)
                PayViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    //-----------onCreateViewHolder: bind view with data model---------
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = adapterDataList[position]
        when (holder) {
            is PhotoViewHolder -> holder.bind(element as Photo)
            is MemoViewHolder -> holder.bind(element as Memo)
            is PayViewHolder -> holder.bind(element as Pay)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val comparable = adapterDataList[position]
        return when (comparable) {
            is Photo -> TYPE_PHOTO
            is Memo -> TYPE_MEMO
            is Pay -> TYPE_PAY
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }
}