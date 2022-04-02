package cn.itbk.smallbox.app

import android.app.Application
import android.util.Log
import cn.itbk.smallbox.BR
import cn.itbk.smallbox.app.base.BaseApplication
import cn.itbk.smallbox.manager.AccountManager
import com.drake.brv.utils.BRV
import com.kongzue.dialogx.DialogX
import io.dcloud.common.util.RuningAcitvityUtil
import io.dcloud.feature.sdk.DCSDKInitConfig
import io.dcloud.feature.sdk.DCUniMPSDK
import io.dcloud.feature.sdk.MenuActionSheetItem
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.cahce.CacheMode
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * @简介 Application
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-29
 */
class SmallBoxAPP : BaseApplication() {

    companion object {
        var instance: Application? = null
            private set
    }

    override fun init() {
        instance = this
        AccountManager.init(applicationContext)
        if (!RuningAcitvityUtil.getAppName(this).contains("io.dcloud.unimp")) {
            // 初始化弹窗
            DialogX.init(this)
            BRV.modelId = BR.m
            //设置读、写、连接超时时间为15s
            val client: OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(6, TimeUnit.SECONDS)
                .readTimeout(6, TimeUnit.SECONDS)
                .writeTimeout(6, TimeUnit.SECONDS)
                .build()
            val cacheDir = File(this.externalCacheDir, "HttpCache")
            RxHttpPlugins.init(client)
                .setCache(cacheDir, 10 * 1024 * 1024, CacheMode.ONLY_NETWORK, 60 * 1000)
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
        ) { b ->
            Log.i("unimp", "onInitFinished----$b")
        }
        //初始化 uni小程序SDK ----end----------



    }


}
