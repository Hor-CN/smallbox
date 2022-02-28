package cn.itbk.smallbox.APP.Base

data class Response<T>(
    var code: Int,
    var message: String,
    var data: T
)