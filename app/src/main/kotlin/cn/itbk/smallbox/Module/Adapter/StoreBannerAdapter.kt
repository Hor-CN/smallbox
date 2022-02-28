package cn.itbk.smallbox.Module.Adapter


import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import cn.itbk.smallbox.model.Srore.Carousel
import cn.itbk.smallbox.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder
import me.virtualiz.blurshadowimageview.BlurShadowImageView


/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-09-08
 */


class StoreBannerAdapter : BaseBannerAdapter<Carousel>() {


    private var onClickListener: OnPageClickListener? = null


    @SuppressLint("ClickableViewAccessibility")
    override fun bindData(
        holder: BaseViewHolder<Carousel>,
        data: Carousel,
        position: Int,
        pageSize: Int
    ) {
        val img: BlurShadowImageView =
            holder.itemView.findViewById(R.id.item_store_banner_item_image)

        Glide.with(img.context)
            .asBitmap()
            //.placeholder(R.drawable.ic_launcher_background) //加载未完成时显示占位图
            .load(data.carouselUrl)
            .into(object : CustomViewTarget<BlurShadowImageView, Bitmap>(img) {
                override fun onLoadFailed(errorDrawable: Drawable?) {}
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    view.setImageBitmap(resource)
                }

                override fun onResourceCleared(placeholder: Drawable?) {}
            })

        img.setOnTouchListener { _: View?, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_MOVE, MotionEvent.ACTION_DOWN -> {
                    img.scaleX = 0.99.toFloat()
                    img.scaleY = 0.99.toFloat()
                }
                MotionEvent.ACTION_UP -> {
                    img.scaleX = 1f
                    img.scaleY = 1f
                }
                else -> {
                    img.scaleX = 1f
                    img.scaleY = 1f
                }
            }
            false
        }
        img.setOnClickListener {
            onClickListener?.onPageClick(position)
        }

    }


    fun setOnClickListener(onClickListener: OnPageClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnPageClickListener {
        fun onPageClick(position: Int)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_store_banner_item
    }


}
