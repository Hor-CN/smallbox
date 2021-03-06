package cn.itbk.smallbox.module.applet

import cn.itbk.smallbox.app.SmallBoxAPP
import kotlinx.coroutines.flow.Flow
import rxhttp.toFlow
import rxhttp.wrapper.param.RxHttp

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-02-25
 */
object AppletRepository {
//    https://gitee.com/Hors/small-box/raw/master/Code/__UNI__5E09467.wgt
    fun downApplet(appId: String, downUrl: String): Flow<String> {
        val saveWgt: String = SmallBoxAPP.instance?.filesDir!!.absolutePath
        return RxHttp.get(downUrl)
            .toFlow("${"$saveWgt/$appId"}.wgt")
    }
}