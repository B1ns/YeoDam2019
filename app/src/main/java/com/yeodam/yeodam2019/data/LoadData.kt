package com.yeodam.yeodam2019.data

import android.graphics.Bitmap
import com.google.android.gms.maps.model.LatLng

data class MapMoreAll(
    var imageView: String? = null,
    var Memo: String? = null,
    var Pay: String? = null,
    var PayInfo: String? = null
)

data class MapMoreMemo(var Memo: String? = null, var MemoLoaction: LatLng? = null)

data class MapMorePhoto(var Photo: Bitmap? = null, var PhotoLocation: LatLng?= null)

data class MapMorePay(var Pay: String? = null, var PayInfo: String? = null, var PayLocation : LatLng? = null)


data class ItemMoreAll(
    var image: String? = null,
    var Memo: String? = null,
    var Pay: String? = null,
    var PayInfo: String? = null
)

data class ItemMoreMemo(var Memo: String? = null)

data class ItemMorePhoto(var Photo: String? = null)

data class ItemMorePay(var Pay: String? = null, var PayInfo: String? = null)