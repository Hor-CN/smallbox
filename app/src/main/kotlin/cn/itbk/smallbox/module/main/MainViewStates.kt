package cn.itbk.smallbox.module.main

import androidx.fragment.app.Fragment
import cn.itbk.smallbox.model.AppUpdateModel
import cn.itbk.smallbox.model.user.User
import cn.itbk.smallbox.module.main.find.FindFragment
import cn.itbk.smallbox.module.main.home.HomeFragment
import cn.itbk.smallbox.module.main.me.MeFragment
import cn.itbk.smallbox.module.main.store.StoreFragment
import io.dcloud.feature.sdk.Interface.IUniMP
import www.sanju.motiontoast.MotionToastStyle


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
//    val Notice: String = "",
    val User: User? = null
)

sealed class MainViewEvent {
    data class AppUpdate(val appUpdateData: AppUpdateModel) : MainViewEvent()
    data class TabsAt(val tabAt: Int) : MainViewEvent()
    data class StateToolbar(val state: Boolean) : MainViewEvent()
    data class MotionToast(val motionToastStyle: MotionToastStyle,val title: String, val message: String) : MainViewEvent()
    data class OpenApplet(val appId: String): MainViewEvent()
    data class UnWgt(val name: String, val filePath:String, val is_run: Boolean): MainViewEvent()
}

sealed class MainViewAction {
    object AppUpdate : MainViewAction()
    data class TabsAt(val tabAt : Int) : MainViewAction()
    class StateToolbar(val state: Boolean) : MainViewAction()
    class MotionToast(val motionToastStyle: MotionToastStyle,val title: String,val message: String) :  MainViewAction()
    data class OpenApplet(val appId: String): MainViewAction()
    data class UnWgt(val name: String, val filePath:String, val is_run: Boolean): MainViewAction()
}
