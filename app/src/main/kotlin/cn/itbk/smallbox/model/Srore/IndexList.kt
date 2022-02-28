package cn.itbk.smallbox.model.Srore

import cn.itbk.smallbox.model.applet.AppletModel


data class ItemList(
    var carousels: ArrayList<Carousel>,
    var goods: ArrayList<Good>
)

data class Carousel(
    var carouselUrl: String,
    var redirectUrl: String,
    var title: String,
    var type: Int
)

data class Good(
    var type: String,
    var value: List<AppletModel>
)
