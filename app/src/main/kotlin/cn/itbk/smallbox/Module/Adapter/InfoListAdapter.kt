package cn.itbk.smallbox.Module.Adapter

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import cn.itbk.smallbox.model.GridMenuItem
import cn.itbk.smallbox.R
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kongzue.dialogx.dialogs.PopTip

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-09-06
 */
class InfoListAdapter(
    data: MutableList<GridMenuItem>? = null
) : BaseQuickAdapter<GridMenuItem, BaseViewHolder>(R.layout.item_rv_menu,data) {
    @SuppressLint("ClickableViewAccessibility")
    override fun convert(holder: BaseViewHolder, item: GridMenuItem) {
        val img: ImageView = holder.itemView.findViewById(R.id.item_rv_menu_icon)
        Glide.with(img.context).load(item.getImg()).into(img)
        holder.setText(R.id.item_rv_menu_title,item.getText())

        val linearLayout: LinearLayout = holder.itemView.findViewById(R.id.item_rv_menu_ll)
        linearLayout.setOnClickListener { v: View? ->
            PopTip.show("正在开发")
        }
        linearLayout.setOnTouchListener { v: View?, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    linearLayout.scaleX = 0.95.toFloat()
                    linearLayout.scaleY = 0.95.toFloat()
                }
                else -> {
                    linearLayout.scaleX = 1f
                    linearLayout.scaleY = 1f
                }
            }
            false
        }


    }
}