package cn.itbk.smallbox.app.base

import rxhttp.wrapper.annotation.DefaultDomain

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-09-17
 */
object BaseConstant {
    // 接口配置
    @DefaultDomain //设置为默认域名
//      const val HOST = "http://10.0.2.2:8080/api"
    const val HOST = "https://smallbox.itbk.cn/api"
    //  http://smallbox.itbk.cn/api/appUpdate
    const val appUpdateUrl = "/appUpdate"
    const val store_index = "/index"
    const val login = "/login"
    const val THIRD_LOGIN = "/third/login"
    const val user = "/user/info"

    // 文件
    const val manifest = "manifest.json"
    const val WGT = ".wgt"

    // 用户文件
    const val TAG_TOKEN = "tag_user_token"
    const val TAG_TOKEN_EXPIRE_TIME = "tag_user_token_expire_time"
    const val TAG_USER_BEAN = "tag_user_bean"
}