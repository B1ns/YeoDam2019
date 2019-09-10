package com.yeodam.yeodam2019.view.adapter.mapMoreAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.yeodam.yeodam2019.data.MapMoreMemo
import android.widget.Toast
import android.location.Geocoder
import android.location.Address
import com.google.android.gms.maps.model.LatLng
import com.yeodam.yeodam2019.R
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class MemoViewAdapter(val context: FragmentActivity?, val mapMoreMemo: ArrayList<MapMoreMemo>) :
    RecyclerView.Adapter<MemoViewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.memo_more, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return mapMoreMemo.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(mapMoreMemo[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val memoTitle: TextView = itemView.findViewById(R.id.more_memo_textView)
        val memoLoaction: TextView = itemView.findViewById(R.id.more_memo_loaction)

        fun bind(mapMoreMemo: MapMoreMemo, context: FragmentActivity?) {

            memoTitle.text = mapMoreMemo.Memo
            memoLoaction.text = getCurrentAddress(mapMoreMemo.MemoLoaction!!)

        }
    }

    fun getCurrentAddress(latlng: LatLng): String {

        //지오코더... GPS를 주소로 변환
        val geocoder = Geocoder(context, Locale.getDefault())

        val addresses: List<Address>?

        try {
            addresses = geocoder.getFromLocation(
                latlng.latitude,
                latlng.longitude,
                1
            )
        } catch (ioException: IOException) {
            //네트워크 문제
            Toast.makeText(context, "서비스 사용불가", Toast.LENGTH_LONG).show()
            return "서비스 사용불가 지역"
        } catch (illegalArgumentException: IllegalArgumentException) {
            Toast.makeText(context, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show()
            return "잘못된 GPS 좌표"

        }


        if (addresses == null || addresses.isEmpty()) {
            Toast.makeText(context, "주소 미발견", Toast.LENGTH_LONG).show()
            return "주소 미발견"

        } else {
            val address = addresses[0]
            return address.getAddressLine(0).toString()
        }
    }
}