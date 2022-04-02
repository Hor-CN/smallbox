package cn.itbk.smallbox.utils

import android.graphics.BlendMode
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import android.os.Build
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import java.security.MessageDigest

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-30
 */
object Utils {
    /**
     * 判断图片颜色是深色还是浅色
     * @param color
     * @return  true 图片为浅色
     */
    fun isLightColor(color: Int): Boolean {
        val darkness =
            1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness < 0.5
    }

    fun setColorFilter(drawable: Drawable, @ColorInt color: Int): Drawable {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
        return drawable
    }

    fun md5(content: String): String {
        val hash = MessageDigest.getInstance("MD5").digest(content.toByteArray())
        val hex = StringBuilder(hash.size * 2)
        for (b in hash) {
            var str = Integer.toHexString(b.toInt())
            if (b < 0x10) {
                str = "0$str"
            }
            hex.append(str.substring(str.length -2))
        }
        return hex.toString()
    }

}