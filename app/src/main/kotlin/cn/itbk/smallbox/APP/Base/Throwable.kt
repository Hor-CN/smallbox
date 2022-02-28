package cn.itbk.smallbox.APP.Base

import cn.itbk.smallbox.R
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.TimeoutCancellationException
import rxhttp.wrapper.exception.HttpStatusCodeException
import rxhttp.wrapper.exception.ParseException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * @简介 Throwable类扩展
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-09-24
 */
val Throwable.code: Int
    get() =
        when (this) {
            is HttpStatusCodeException -> this.statusCode //Http状态码异常
            is ParseException -> this.errorCode.toIntOrNull() ?: -1     //业务code异常
            else -> -1
        }


val Throwable.msg: Int
    get() =
        when(this) {
            //网络异常
            is UnknownHostException -> R.string.UnknownHostException
            //okhttp全局设置超时,//rxjava中的timeout方法超时,/协程超时
            is SocketTimeoutException,
            is TimeoutException,
            is TimeoutCancellationException -> R.string.TimeoutException

            is ConnectException -> R.string.ConnectException
            //请求失败异常
            is HttpStatusCodeException -> R.string.HttpStatusCodeException
            //请求成功，但Json语法异常,导致解析失败
            is JsonSyntaxException -> R.string.JsonSyntaxException
            // ParseException异常表明请求成功，但是数据不正确
            is ParseException -> {
                // this.message ?: errorCode   msg为空，显示code
                R.string.error_msg
            }
            else -> {
                R.string.error_msg
            }
        }


