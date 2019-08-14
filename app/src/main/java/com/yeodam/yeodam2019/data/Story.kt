package com.yeodam.yeodam2019.data

import android.media.Image

data class Story(val image: Image, val title: String, val hashtag: String)

data class UserDTO(val userName: String, val userImage: String)
