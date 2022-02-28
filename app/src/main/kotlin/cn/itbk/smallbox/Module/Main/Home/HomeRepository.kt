package cn.itbk.smallbox.Module.Main.Home


import cn.itbk.smallbox.APP.Base.BaseConstant
import cn.itbk.smallbox.APP.SmallBoxAPP
import cn.itbk.smallbox.model.applet.AppletConfigModel
import cn.itbk.smallbox.Utils.Utils
import io.dcloud.feature.sdk.DCUniMPSDK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
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

    suspend fun deleteApplet(path: String) {
        return withContext(Dispatchers.IO) {
            Utils.deleteFile(File(path))
        }
    }

}