package cn.itbk.smallbox.model

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-09-06
 */
class GridMenuItem(img: Int, text: String) {
    private var img: Int
    private var text: String
    fun getImg(): Int {
        return img
    }

    fun setImg(img: Int) {
        this.img = img
    }

    fun getText(): String {
        return text
    }

    fun setText(text: String) {
        this.text = text
    }

    init {
        this.img = img
        this.text = text
    }
}