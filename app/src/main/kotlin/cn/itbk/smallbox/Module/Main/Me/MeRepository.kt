package cn.itbk.smallbox.Module.Main.Me

import android.util.Log
import cn.itbk.smallbox.APP.Base.BaseConstant
import cn.itbk.smallbox.model.User.User
import kotlinx.coroutines.flow.Flow
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
//          return flow<User> {
//               val userFile = File(SmallBoxAPP.instance!!.filesDir.path + BaseConfig.userFile)
//               if (!userFile.exists()) {
//
//               }
//          }.flowOn(Dispatchers.IO)
        Log.d("登录", "login: ${username+""+password}")
        return RxHttp.postJson(BaseConstant.login)
            .add("username", username)
            .add("password", password)
            .add("device", "android")
            .toFlowResponse()
    }


}