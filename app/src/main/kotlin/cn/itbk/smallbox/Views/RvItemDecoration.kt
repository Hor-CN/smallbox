package cn.itbk.smallbox.Views

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView

/**
 * @简介 RecyclerView列表分割线
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-02-27
 */
class RvItemDecoration : ItemDecoration() {
    private var mDividerHeight = 1f //线的高度
    private val mPaint: Paint = Paint()//画笔将自己做出来的分割线矩形画出颜色
    private var mLeftMargin: Float = 0f  // 左边距
    private var mRightMargin: Float = 0f  // 右边距

    init {
        mPaint.isAntiAlias = true //抗锯齿
        mPaint.color = Color.GRAY //默认颜色
    }

    //设置左右偏移
    fun setMargin(mLeftMargin: Float, mRightMargin: Float): RvItemDecoration {
        this.mLeftMargin = mLeftMargin
        this.mRightMargin = mRightMargin
        return this
    }


    //设置颜色
    fun setColor(color: Int): RvItemDecoration {
        mPaint.color = color
        return this
    }

    //设置分割线高度
    fun setDividerHeight(height: Float): RvItemDecoration {
        mDividerHeight = height
        return this
    }

    //在这里就已经把宽度的偏移给做好了
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        //第一个ItemView不需要在上面绘制分割线
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.top = mDividerHeight.toInt() //指相对itemView顶部的偏移量
        }
    }

    //这里主要是绘制颜色的
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        //因为getItemOffsets是针对每一个ItemView，而onDraw方法是针对RecyclerView本身，所以需要循环遍历来设置
        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            val index = parent.getChildAdapterPosition(view)
            //第一个ItemView不需要绘制
            if (index == 0) {
                continue
            }
            val dividerTop = view.top - mDividerHeight
            val dividerLeft = parent.paddingLeft + mLeftMargin
            val dividerBottom = view.top.toFloat()
            val dividerRight = parent.width - parent.paddingRight - mRightMargin
            c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, mPaint)
        }
    }


}