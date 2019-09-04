package com.yeodam.yeodam2019.data

data class MapMoreAll(
    var imageView: String? = null,
    var Memo: String? = null,
    var Pay: String? = null,
    var PayInfo: String? = null
)

data class MapMoreMemo(var Memo: String? = null, var MemoLoaction: String? = null)

data class MapMorePhoto(var Photo: String? = null)

data class MapMorePay(var Pay: String? = null, var PayInfo: String? = null)


data class ItemMoreAll(
    var image: String? = null,
    var Memo: String? = null,
    var Pay: String? = null,
    var PayInfo: String? = null
)

data class ItemMoreMemo(var Memo: String? = null)

data class ItemMorePhoto(var Photo: String? = null)

data class ItemMorePay(var Pay: String? = null, var PayInfo: String? = null)