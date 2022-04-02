package cn.itbk.smallbox.app.databinding

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-02-28
 */
object ImageViewAttrAdapter {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String?) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(view.context)
                .load(url)
                .into(view)
        }
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: Int?) {
        if (url != null) {
            Glide.with(view.context)
                .load(url)
                .into(view)
        }
    }
}