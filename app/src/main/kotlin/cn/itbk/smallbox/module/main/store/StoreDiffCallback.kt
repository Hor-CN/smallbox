package cn.itbk.smallbox.module.main.store

import androidx.recyclerview.widget.DiffUtil
import cn.itbk.smallbox.model.applet.AppletConfigModel
import cn.itbk.smallbox.model.applet.AppletModel
import cn.itbk.smallbox.model.store.Good
import com.drake.brv.listener.ItemDifferCallback

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-09-18
 */
class StoreDiffCallback : ItemDifferCallback {

    /**
     * 判断是否是同一个item
     */
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return if (oldItem is Good && newItem is Good) {
            oldItem.title == newItem.title
        } else super.areItemsTheSame(oldItem, newItem)
    }

    /**
     * 当是同一个item时，再判断内容是否改变
     */
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {

        return if (oldItem is Good && newItem is Good) {
           oldItem.value.size == newItem.value.size
        } else super.areContentsTheSame(oldItem, newItem)
    }
}