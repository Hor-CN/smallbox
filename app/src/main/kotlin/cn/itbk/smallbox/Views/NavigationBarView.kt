package cn.itbk.smallbox.Views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import cn.itbk.smallbox.R

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-09-01
 */
class NavigationBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr) {
    private var mNavigationBar: OnNavigationBarListener? = null
    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.view_navigationbar, this)
        val lvLeftBtn = findViewById<LinearLayout>(R.id.navigation_bar_left_lv)
        val lvRightBtn = findViewById<LinearLayout>(R.id.navigation_bar_right_lv)
        lvLeftBtn.setOnClickListener { v: View? ->
            mNavigationBar!!.OnLeftClick(
                v
            )
        }
        lvRightBtn.setOnClickListener { v: View? ->
            mNavigationBar!!.OnRightClick(
                v
            )
        }
    }

    // 监听器
    fun setOnNavigationBarListener(navigationBarListener: OnNavigationBarListener?) {
        mNavigationBar = navigationBarListener
    }

    // 接口
    interface OnNavigationBarListener {
        fun OnLeftClick(v: View?)
        fun OnRightClick(v: View?)
    }

    init {
        init(context)
    }
}
