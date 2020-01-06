package com.yeodam.yeodam2019.utils

import android.net.Uri
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class Job(uri: Uri) : Any(), ClusterItem {

    private var lat: Double = 0.0
    private var lon: Double = 0.0
    private var imageUri = uri


    fun getUri(): String{
        return imageUri.toString()
    }

    fun getName() : String{
        return "여담"
    }


    override fun getSnippet(): String? {
        return null
    }

    override fun getTitle(): String? {
        return null
    }


    override fun getPosition(): LatLng {
        return LatLng(lat, lon)
    }

}