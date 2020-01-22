package com.yeodam.yeodam2019.data

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

data class Story(
    var firstTitle: String? = null,
    var image: String? = null,
    var ImageCount: Int? = null,
    var title: String? = null,
    var hashtag: String? = null,
    var index: Int? = null,
    var day: String? = null
)

data class UserDTO(var userImage: String? = null, var userName: String? = null)

data class userTitle(var title: String? = null, val story: String? = null)

data class YeoDam(
    var Map: ArrayList<LatLng>? = null,
    var Memo: ArrayList<String>? = null,
    var MemoLocation: ArrayList<LatLng>? = null,
    var Photo: ArrayList<String>? = null,
    var PhotoLocation: ArrayList<LatLng>? = null,
    var Pay: ArrayList<String>? = null,
    var PayInfo: ArrayList<String>? = null,
    var PayLocation: ArrayList<LatLng>? = null,
    var StoryIndex: Int? = null
)


@Parcelize
data class YeoDamData(
    var Map: ArrayList<LatLng>? = null,
    var MemoLocation: ArrayList<LatLng>? = null,
    var Photo: ArrayList<Bitmap>? = null,
    var PhotoLocation: ArrayList<LatLng>? = null,
    var PayLocation: ArrayList<LatLng>? = null,
    var PhotoUri: ArrayList<Uri>? = null
) : Parcelable

@Parcelize
data class latData(
    var lat: ArrayList<Double>
) : Parcelable

@Parcelize
data class lonData(
    var lon: ArrayList<Double>
) : Parcelable

data class userCount(var DayCount: Int? = null, var KmCount: Int? = null)

data class moreData(
    var Memo: ArrayList<String>? = null,
    var Pay: ArrayList<String>?,
    var PayInfo: ArrayList<String>?,
    var Photo: ArrayList<Bitmap>? = null
)
