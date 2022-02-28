package cn.itbk.smallbox.Module.Main.Home

import cn.itbk.smallbox.APP.State.PageState
import cn.itbk.smallbox.model.applet.AppletConfigModel

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-01-13
 */
data class HomeViewState(
    val appletList : List<AppletConfigModel> = emptyList(),
    val pageState : PageState = PageState.Loading
)

sealed class HomeViewEvent {
//    data class DeleteApplet(val path: String) : HomeViewEvent()
}

sealed class HomeViewAction {
    object LoadApplets : HomeViewAction() // 列表
    data class DeleteApplet(val path: String) : HomeViewAction() // 删除
    object OnRefresh : HomeViewAction() // 刷新
}