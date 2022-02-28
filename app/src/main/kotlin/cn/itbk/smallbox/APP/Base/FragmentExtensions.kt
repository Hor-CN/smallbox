package cn.itbk.smallbox.APP.Base

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-30
 */
fun Fragment.setSupportActionBar(toolbar: Toolbar) {
    val parentActivity = activity as? AppCompatActivity
    parentActivity?.setSupportActionBar(toolbar)
}

val Fragment.supportActionBar: ActionBar?
    get() = (activity as? AppCompatActivity)?.supportActionBar