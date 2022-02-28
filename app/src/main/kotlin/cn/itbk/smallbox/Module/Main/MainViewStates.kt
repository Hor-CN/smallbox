package cn.itbk.smallbox.Module.Main

import androidx.fragment.app.Fragment
import cn.itbk.smallbox.model.AppUpdateModel
import cn.itbk.smallbox.model.User.User
import cn.itbk.smallbox.Module.Main.Find.FindFragment
import cn.itbk.smallbox.Module.Main.Home.HomeFragment
import cn.itbk.smallbox.Module.Main.Me.MeFragment
import cn.itbk.smallbox.Module.Main.Store.StoreFragment


/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-01-12
 */
data class MainViewState(
    val fragments : MutableList<Fragment> = listOf(
        HomeFragment(),
        StoreFragment(),
        FindFragment(),
        MeFragment()
    ) as MutableList<Fragment>,
    val Notice: String = "",
    val User: User? = null
)

sealed class MainViewEvent {
    data class AppUpdate(val appUpdateData: AppUpdateModel) : MainViewEvent()
    data class TabsAt(val tabAt: Int) : MainViewEvent()
    data class StateToolbar(val state: Boolean) : MainViewEvent()
}

sealed class MainViewAction {
    object AppUpdate : MainViewAction()
    data class TabsAt(val tabAt : Int) : MainViewAction()
    class StateToolbar(val state: Boolean) : MainViewAction()
}
