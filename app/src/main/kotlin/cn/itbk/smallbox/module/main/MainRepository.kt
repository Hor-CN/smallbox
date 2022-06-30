package cn.itbk.smallbox.module.main

import android.util.Log
import cn.itbk.smallbox.app.base.BaseConstant
import cn.itbk.smallbox.model.AppUpdateModel
import cn.itbk.smallbox.model.user.User
import kotlinx.coroutines.flow.Flow
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse


/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-09-17
 */
object MainRepository {

     fun appUpdate() : Flow<AppUpdateModel> {
        return RxHttp.get(BaseConstant.appUpdateUrl)
                  .connectTimeout(3000)
                .toFlowResponse()
    }





}
