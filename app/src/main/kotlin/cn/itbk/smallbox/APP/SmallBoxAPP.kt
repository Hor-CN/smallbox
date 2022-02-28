package cn.itbk.smallbox.APP

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import cn.itbk.smallbox.APP.Base.BaseApplication
import cn.itbk.smallbox.BR
import cn.itbk.smallbox.Manager.AccountManager
import com.drake.brv.utils.BRV
import com.kongzue.dialogx.DialogX
import io.dcloud.common.util.RuningAcitvityUtil
import io.dcloud.feature.sdk.DCSDKInitConfig
import io.dcloud.feature.sdk.DCUniMPSDK
import io.dcloud.feature.sdk.MenuActionSheetItem

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-29
 */
class SmallBoxAPP : BaseApplication() {

    companion object {
        var instance: Application? = null
            private set
        val packageNameImpl: String
            get() {
                var sPackageName = instance!!.packageName
                if (sPackageName.contains(":")) {
                    sPackageName = sPackageName.substring(0, sPackageName.lastIndexOf(":"))
                }
                return sPackageName
            }
    }

    override fun init() {
        instance = this
        AccountManager.init(applicationContext)
        if (!RuningAcitvityUtil.getAppName(this).contains("io.dcloud.unimp")) {
            // 初始化弹窗
            DialogX.init(this)
            BRV.modelId = BR.m
        }

        //初始化 uni小程序SDK ----start----------
        val item = MenuActionSheetItem("正在开发", "gy")
        val sheetItems: MutableList<MenuActionSheetItem> = ArrayList()
        sheetItems.add(item)
        val config = DCSDKInitConfig.Builder()
            .setCapsule(true)
            .setMenuActionSheetItems(sheetItems)
            .setEnableBackground(true) //开启后台运行
            .build()
        DCUniMPSDK.getInstance().initialize(
            this, config
        ) { b -> Log.i("unimp", "onInitFinished----$b") }
        //初始化 uni小程序SDK ----end----------


    }


}
