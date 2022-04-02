package cn.itbk.smallbox.module.main.me

import cn.itbk.smallbox.app.state.PageState
import cn.itbk.smallbox.model.user.User

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-01-15
 */
data class MeViewState(
    val User: User? = null,
    val pageState: PageState = PageState.Loading
)

sealed class MeViewEvent {
    object DismissDialog : MeViewEvent()
    object NotLoggedIn : MeViewEvent()
    data class ToastEvent(val int: Int) : MeViewEvent()
}

sealed class MeViewAction {
    object CurrentUser : MeViewAction()
    data class Login(val username: String, val password: String) : MeViewAction()

}
