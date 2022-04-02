package cn.itbk.smallbox.module.main.home


import cn.itbk.smallbox.app.base.BaseConstant
import cn.itbk.smallbox.app.SmallBoxAPP
import cn.itbk.smallbox.model.applet.AppletConfigModel
import io.dcloud.feature.sdk.DCUniMPSDK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import rxhttp.wrapper.utils.GsonUtil
import java.io.File

/**
 * @简介
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-29
 */
object HomeRepository {

    fun fetchApplets(): Flow<List<AppletConfigModel>> {
        return flow<List<AppletConfigModel>> {
            val list = mutableListOf<AppletConfigModel>()
            val path: String = DCUniMPSDK.getInstance().getAppBasePath(SmallBoxAPP.instance)
            val file = File(path)
            file.walk().maxDepth(3)
                .filter { it.isFile }
                .filter { it.name == BaseConstant.manifest }
                .forEach {
                    val item = GsonUtil.fromJson<AppletConfigModel>(
                        it.readText(),
                        AppletConfigModel::class.java
                    )
                    item.icon = "$path/${item.id}/www/static/logo.png"
                    list.add(item)
                }

            emit(list)
        }.flowOn(Dispatchers.IO)
    }

    fun deleteApplet(path: String): Flow<Boolean> {
        return flow {
            val files = DCUniMPSDK.getInstance().getAppBasePath(SmallBoxAPP.instance) + "/" + path
            emit(
                File(files).walkBottomUp()
                    .fold(true, { res, it -> (it.delete() || !it.exists()) && res })
            )
        }.flowOn(Dispatchers.IO)
    }

}