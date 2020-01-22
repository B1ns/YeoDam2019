package com.yeodam.yeodam2019.data

import android.graphics.Bitmap
import com.google.android.gms.maps.model.LatLng

data class Photo(var Photo: Bitmap? = null)

data class Memo(var Memo: String? = null, var memoLocation: LatLng? = null)

data class Pay(var Pay: String? = null, var PayInfo: String? = null, var payLocation: LatLng? = null)
