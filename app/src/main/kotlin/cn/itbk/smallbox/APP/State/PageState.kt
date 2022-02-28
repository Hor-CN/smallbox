package cn.itbk.smallbox.APP.State

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-01-06
 */
sealed class PageState {
    object Loading : PageState()
    object Success : PageState()
    data class Error(val message: Int) : PageState()
    data class Empty(val message: Int) : PageState()
}
