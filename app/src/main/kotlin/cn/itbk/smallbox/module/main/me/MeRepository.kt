package cn.itbk.smallbox.module.main.me

import android.util.Log
import cn.itbk.smallbox.app.base.BaseConstant
import cn.itbk.smallbox.model.user.User
import cn.itbk.smallbox.utils.Utils
import io.dcloud.common.util.Md5Utils
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import rxhttp.toFlow
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-09-15
 */
object MeRepository {

    fun login(username: String, password: String): Flow<User> {
        Log.d("登录", "login: ${username+"密码："+Utils.md5(password)}")
        return RxHttp.postJson(BaseConstant.login)
            .add("loginName", username)
            .add("passwordMd5", Utils.md5(password))
            .add("device", "android")
            .toFlowResponse()
    }


}