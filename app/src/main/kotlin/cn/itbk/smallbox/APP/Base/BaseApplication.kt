package cn.itbk.smallbox.APP.Base

import android.app.Application

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-02-08
 */
abstract class BaseApplication : Application() {



    override fun onCreate() {
        super.onCreate()
        init()
    }

    abstract fun init()
}