package com.yeodam.yeodam2019.view.adapter.mapMoreAdapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.yeodam.yeodam2019.view.activity.more.PayInfoActivity
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.MapMorePay


class PayViewAdapter(val context: FragmentActivity?, val mapMorePay: ArrayList<MapMorePay>) :
    RecyclerView.Adapter<PayViewAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.pay_more, parent, false)
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
        val payLayout : LinearLayout = itemView.findViewById(R.id.more_pay_layout)

        fun bind(mapMorePay: MapMorePay, context: FragmentActivity?) {

            pay.text = mapMorePay.Pay
            payInfo.text = mapMorePay.PayInfo

            payLayout.setOnClickListener {
                val intent = Intent(context, PayInfoActivity::class.java)
                intent.putExtra("Pay", mapMorePay.Pay)
                intent.putExtra("PayInfo", mapMorePay.PayInfo)
                intent.putExtra("PayLocation", mapMorePay.PayLocation)
                ContextCompat.startActivity(context!!, intent, Bundle())
            }

        }
    }
}