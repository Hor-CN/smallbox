package cn.itbk.smallbox.module.applet

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.itbk.smallbox.app.base.SingleLiveEvents
import cn.itbk.smallbox.app.base.asLiveData
import cn.itbk.smallbox.app.base.msg
import cn.itbk.smallbox.app.base.setEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-02-25
 */
class AppletViewModel : ViewModel() {

    private val _viewStates = MutableLiveData(AppletViewState())
    val viewStates = _viewStates.asLiveData()
    private val _viewEvents: SingleLiveEvents<AppletViewEvent> = SingleLiveEvents()
    val viewEvents = _viewEvents.asLiveData()

    fun dispatch(viewAction: AppletViewAction) {
        when (viewAction) {
            is AppletViewAction.DownApplet -> downApplet(viewAction.appId, viewAction.downUrl)
            is AppletViewAction.RunApplet -> {
                _viewEvents.setEvent(AppletViewEvent.RunApplet(viewAction.appId, viewAction.downUrl))
            }
        }
    }

    private fun downApplet(appId: String, downUrl: String) {
        viewModelScope.launch {
//            if (DCUniMPSDK.getInstance().isExistsApp(appId)) {
//                // DCUniMPSDK.getInstance().getAppVersionInfo(appId)
//            }
            AppletRepository.downApplet(appId, downUrl)
                .catch {
                    Log.d("TAG", "downApplet: 下载失败${it.msg}")
                }
                .collect {
                    // 下载小程序资源文件完成后
                    Log.d("TAG", "downApplet: 下载完成${it}")
                    dispatch(AppletViewAction.RunApplet(appId, it))
                }

        }
    }

}