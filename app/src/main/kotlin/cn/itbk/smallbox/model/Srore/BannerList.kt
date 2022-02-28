package cn.itbk.smallbox.model.Srore

/**
 * @简介 轮播图数据模型
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-09-08
 */
class BannerList : ArrayList<BannerModel>()


data class BannerModel(
    var imagePath: String,
    var title: String,
    var url: String
)