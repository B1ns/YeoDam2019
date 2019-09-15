package com.yeodam.yeodam2019.view.fragment.map


import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.yeodam.yeodam2019.R
import com.yeodam.yeodam2019.data.MapMorePhoto
import com.yeodam.yeodam2019.view.adapter.mapMoreAdapter.PhotoViewAdapter

open class MapMorePhotoFragment : Fragment() {

    private val photoStory = arrayListOf<MapMorePhoto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_map_more_photo, container, false)

        val mAdapter = PhotoViewAdapter(activity, photoStory)
        val mLayoutManager = GridLayoutManager(context, 3)
        val recyclerView = v.findViewById<RecyclerView>(R.id.map_photo_recyclerView)

        val extra = arguments

        val photo = extra?.getParcelableArrayList<Bitmap>("Photo")

        var i = 0

        if(photo != null) {
            while (i < photo.size){
                addItem(i)
                i++
            }
        }

        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = mAdapter

        return v
    }

    private fun addItem(i: Int) {
        val extra = arguments

        val photo = extra?.getParcelableArrayList<Bitmap>("Photo")
        val photoLocation = extra?.getParcelableArrayList<LatLng>("PhotoLocation")

        photoStory.add(MapMorePhoto(photo?.get(i), photoLocation?.get(i)))
    }


}
