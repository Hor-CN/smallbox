package cn.itbk.smallbox.app.base

import android.util.Log
import cn.itbk.smallbox.module.main.me.MeViewAction
import com.tencent.tauth.IUiListener
import com.tencent.tauth.UiError
import org.json.JSONObject

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-04-06
 */
class BaseUiListener : IUiListener{

    override fun onComplete(response: Any?) {

        val jsonResp = response as JSONObject
        Log.d("QQ登录", "$jsonResp")

        MeViewAction.QQLogin(jsonResp["access_token"].toString())
    }

    override fun onError(error: UiError?) {
        Log.d("QQ登录", "onError: 错误 $error")
    }

    override fun onCancel() {
        Log.d("QQ登录", "取消")
    }

    override fun onWarning(i: Int) {
        Log.d("QQ登录", "onWarning: $i")
    }

}