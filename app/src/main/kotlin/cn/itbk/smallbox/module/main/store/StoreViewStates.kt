package cn.itbk.smallbox.module.main.store

import cn.itbk.smallbox.R
import cn.itbk.smallbox.app.state.PageState
import cn.itbk.smallbox.model.store.Carousel
import cn.itbk.smallbox.model.store.Good
import cn.itbk.smallbox.model.store.GridMenuItem

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-01-15
 */
data class StoreViewState(
    val pageState: PageState = PageState.Loading,
    val bannerList: List<Carousel> = emptyList(),
    val gridMenuList: List<GridMenuItem> = ArrayList<GridMenuItem>().apply {
        add(GridMenuItem(R.drawable.ic_selected, "精选"))
        add(GridMenuItem(R.drawable.ic_classify, "分类"))
        add(GridMenuItem(R.drawable.ic_ranking, "排行"))
        add(GridMenuItem(R.drawable.ic_special, "专题"))
    },
    val contentList: List<Good> = emptyList()
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