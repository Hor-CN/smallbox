package cn.itbk.smallbox.module.applet

import cn.itbk.smallbox.model.applet.AppletModel

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-02-25
 */

data class AppletViewState(
    val data : AppletModel? = null
)

sealed class AppletViewEvent {
    data class RunApplet(val appId: String, val downUrl: String) : AppletViewEvent()
}

sealed class AppletViewAction {
    data class DownApplet(val appId: String, val downUrl: String) : AppletViewAction()
    data class RunApplet(val appId: String, val downUrl: String) : AppletViewAction()
}