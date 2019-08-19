package com.yeodam.yeodam2019.data

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

data class Story(val image: Uri, val ImageCount: Int, val title: String, val hashtag: String)

data class UserDTO(var userImage: String? = null, var userName: String? = null)

data class memo(var memo: String? = null, var myLocation: HashMap<String, Any>? = null)

data class UploadStory(
    var storyImage: String? = null,
    var storyTitle: String? = null,
    var storyCountry: String? = null,
    var storyDay: String? = null
)

data class YeoDam(
    var Map: ArrayList<LatLng>? = null,
    var Memo: ArrayList<String>? = null,
    var MemoLocation: ArrayList<LatLng>? = null,
    var Photo: ArrayList<Bitmap>? = null,
    var PhotoLocation: ArrayList<LatLng>? = null,
    var Pay: ArrayList<String>? = null,
    var PayInfo: ArrayList<String>? = null,
    var PayLocation: ArrayList<LatLng>? = null
)

@Parcelize
data class YeoDamData(
    var Map: ArrayList<LatLng>? = null,
    var MemoLocation: ArrayList<LatLng>? = null,
    var Photo: ArrayList<Bitmap>? = null,
    var PhotoLocation: ArrayList<LatLng>? = null,
    var PayLocation: ArrayList<LatLng>? = null
) : Parcelable