package com.yeodam.yeodam2019.view.adapter.mapMoreAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.MapMorePay

class PayViewAdapter(val context: Context, val mapMorePay: ArrayList<MapMorePay>) :
    RecyclerView.Adapter<PayViewAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.more_pay_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return mapMorePay.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(mapMorePay[position], context)
    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val pay : TextView = itemView.findViewById(R.id.more_pay)
        val payInfo : TextView = itemView.findViewById(R.id.more_pay_info)

        fun bind(mapMorePay: MapMorePay, context: Context) {

            pay.text = mapMorePay.Pay
            payInfo.text = mapMorePay.PayInfo

        }
    }
}