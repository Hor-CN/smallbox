package cn.itbk.smallbox.module.main.home

import cn.itbk.smallbox.model.applet.AppletConfigModel
import com.drake.brv.listener.ItemDifferCallback

/**
 * @简介 首页的无刷新更新Diff数据
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-29
 */
class HomeDiffCallback : ItemDifferCallback {

    /**
     * 判断是否是同一个item
     */
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return if (oldItem is AppletConfigModel && newItem is AppletConfigModel) {
            oldItem.id == newItem.id
        } else super.areItemsTheSame(oldItem, newItem)
    }

    /**
     * 当是同一个item时，再判断内容是否改变
     */
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return if (oldItem is AppletConfigModel && newItem is AppletConfigModel) {
            oldItem.icon == newItem.icon &&
                    oldItem.name == newItem.name &&
                    oldItem.description == newItem.description
        } else super.areContentsTheSame(oldItem, newItem)
    }
}