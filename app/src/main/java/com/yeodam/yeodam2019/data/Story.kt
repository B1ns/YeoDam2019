package com.yeodam.yeodam2019.data

import android.net.Uri

data class Story(val image: String, val ImageCount: Int, val title: String, val hashtag: String)

data class UserDTO(var userImage: String? = null, var userName: String? = null)

data class memo(var memo: String? = null, var myLocation: HashMap<String, Any>? = null)
