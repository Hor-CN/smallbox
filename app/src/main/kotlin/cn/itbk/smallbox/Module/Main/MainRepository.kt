package cn.itbk.smallbox.Module.Main

import cn.itbk.smallbox.APP.Base.BaseConstant
import cn.itbk.smallbox.model.AppUpdateModel
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
                .toFlowResponse()
    }


}
