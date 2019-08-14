package com.yeodam.yeodam2019.data

import android.media.Image

data class Story(val image: Image, val title: String, val hashtag: String)

data class UserDTO(var userImage: String? = null, var userName: String? = null)

data class memo(var memo: String? = null, var myLocation: HashMap<String, Any>? = null)