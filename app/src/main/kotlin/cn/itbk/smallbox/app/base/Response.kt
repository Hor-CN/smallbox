package cn.itbk.smallbox.app.base

data class Response<T>(
    var code: Int,
    var message: String,
    var data: T
)