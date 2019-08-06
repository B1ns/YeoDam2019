//package com.yeodam.yeodam2019.view.adapter
//
//import android.R
//import android.content.Context
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import android.view.ViewGroup
//import android.view.LayoutInflater
//import android.view.View
//
//
//class CardRecyclerAdapter
//internal constructor(context: Context, private val mData: Array<String>) :
//    RecyclerView.Adapter<CardRecyclerAdapter.ViewHolder>() {
//    private val mInflater: LayoutInflater = LayoutInflater.from(context)
//    private var mClickListener: ItemClickListener? = null
//
//    // inflates the cell layout from xml when needed
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = mInflater.inflate(R.layout.card_item, parent, false)
//        return ViewHolder(view)
//    }
//
//    // binds the data to the TextView in each cell
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.myTextView.text = mData[position]
//    }
//
//    // total number of cells
//    override fun getItemCount(): Int {
//        return mData.size
//    }
//
//
//    // stores and recycles views as they are scrolled off screen
//    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
//        View.OnClickListener {
//        internal var myTextView: TextView = itemView.findViewById(R.id.info_text)
//
//        init {
//            itemView.setOnClickListener(this)
//        }
//
//        override fun onClick(view: View) {
//            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
//        }
//    }
//
//    // convenience method for getting data at click position
//    internal fun getItem(id: Int): String {
//        return mData[id]
//    }
//
//    // allows clicks events to be caught
//    internal fun setClickListener(itemClickListener: ItemClickListener) {
//        this.mClickListener = itemClickListener
//    }
//
//    // parent activity will implement this method to respond to click events
//    interface ItemClickListener {
//        fun onItemClick(view: View, position: Int)
//    }
//}