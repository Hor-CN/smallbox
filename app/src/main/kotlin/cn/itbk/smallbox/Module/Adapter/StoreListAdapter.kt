package cn.itbk.smallbox.Module.Adapter

import android.widget.ImageView
import cn.itbk.smallbox.R
import cn.itbk.smallbox.model.Srore.ListModel
import cn.itbk.smallbox.model.applet.AppletModel
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class StoreListAdapter(sectionHeadResId: Int, layoutResId: Int, data: List<ListModel?>?) :
    BaseSectionQuickAdapter<ListModel, BaseViewHolder>(sectionHeadResId, layoutResId,
        data as MutableList<ListModel>?
    ) {
    override fun convertHeader(baseViewHolder: BaseViewHolder, listModel: ListModel) {
        if (listModel.getObject() is String) {
            baseViewHolder.setText(R.id.item_store_head_title, listModel.getObject() as String)
        }
    }

    override fun convert(baseViewHolder: BaseViewHolder, listModel: ListModel) {
        val (_, name, author, description, _, download_num, comment_num, like_num, create_time) = listModel.getObject() as AppletModel
        baseViewHolder.setText(R.id.item_store_content_AppName, name)
        baseViewHolder.setText(R.id.item_store_content_tip, description)
        val img = baseViewHolder.itemView.findViewById<ImageView>(R.id.item_store_content_img)
        Glide.with(img.context)
            .load(author.imgPath)
            .placeholder(R.drawable.ic_author) //加载未完成时显示占位图
            .into(img)
        baseViewHolder.setText(R.id.item_store_content_name, author.nickname)
        baseViewHolder.setText(R.id.item_store_content_time, create_time)
        baseViewHolder.setText(R.id.item_store_content_DownloadNum, download_num.toString() + "")
        baseViewHolder.setText(R.id.item_store_content_gieUPNum, like_num.toString() + "")
        baseViewHolder.setText(R.id.item_store_content_commentNum, comment_num.toString() + "")
        //baseViewHolder.setBackgroundColor(R.id.item_store_content_DownBT, Color.parseColor("#E2975C"));
    }

    init {
        setNormalLayout(layoutResId)
    }
}