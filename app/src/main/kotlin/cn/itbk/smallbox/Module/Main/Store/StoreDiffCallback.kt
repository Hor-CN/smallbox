package cn.itbk.smallbox.Module.Main.Store

import androidx.recyclerview.widget.DiffUtil
import cn.itbk.smallbox.model.Srore.ListModel

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-09-18
 */
class StoreDiffCallback : DiffUtil.ItemCallback<ListModel>() {

    /**
     * 判断是否是同一个item
     */
    override fun areItemsTheSame(oldItem: ListModel, newItem: ListModel): Boolean {
        return oldItem.`object` == newItem.`object`
    }

    /**
     * 当是同一个item时，再判断内容是否改变
     */
    override fun areContentsTheSame(oldItem: ListModel, newItem: ListModel): Boolean {
        return oldItem.isHeader == newItem.isHeader
    }
}