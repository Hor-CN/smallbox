package cn.itbk.smallbox.Module.Main.Store

import cn.itbk.smallbox.APP.State.PageState
import cn.itbk.smallbox.model.Srore.Carousel
import cn.itbk.smallbox.model.Srore.ListModel

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-01-15
 */
data class StoreViewState(
    val pageState: PageState = PageState.Loading,
    val bannerList: List<Carousel> = emptyList(),
    val contentList: List<ListModel> = emptyList()
)

sealed class StoreViewEvent {
//    data class LoadApplets(
//        val contentList: MutableList<ListModel>,
//        val bannerList: MutableList<Carousel>
//    ) : StoreViewEvent()


}

sealed class StoreViewAction {
    object LoadApplets : StoreViewAction()
}