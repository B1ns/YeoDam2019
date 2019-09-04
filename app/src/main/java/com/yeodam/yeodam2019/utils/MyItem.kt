package com.yeodam.yeodam2019.utils

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class MyItem : ClusterItem {

    private val location : LatLng? = null
//    private val

    override fun getSnippet(): String {
        return ""
    }

    override fun getTitle(): String {
        return ""
    }

    override fun getPosition(): LatLng? {
        return null
    }

}