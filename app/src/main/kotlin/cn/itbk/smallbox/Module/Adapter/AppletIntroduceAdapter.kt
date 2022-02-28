package cn.itbk.smallbox.Module.Adapter

import cn.itbk.smallbox.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-02-23
 */
class AppletIntroduceAdapter(
    data: MutableList<String>? = null
) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_applet_introduce_rv_item, data) {
    override fun convert(holder: BaseViewHolder, item: String) {

    }


}