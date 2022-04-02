package cn.itbk.smallbox.model.store

import cn.itbk.smallbox.model.applet.AppletModel


data class ItemList(
    var carousels: ArrayList<Carousel>,
    var goods: ArrayList<Good>
)

class CarouselModel(
    var listNested : List<Carousel>
)

class GridMenuModel(
    var listNested : List<GridMenuItem>
)

data class Carousel(
    var carouselUrl: String,
    var redirectUrl: String,
    var title: String,
    var type: Int
)


data class GridMenuItem(
    var img: Int,
    var text: String
)

data class Good(
    var title: String,
    var value: List<AppletModel>
)

