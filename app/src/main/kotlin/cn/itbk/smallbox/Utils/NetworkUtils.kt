package cn.itbk.smallbox.Utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings

/**
 * @简介 网络工具
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-09-24
 */
object NetworkUtils {

    /**
     * 打开 wifi 设置界面 requestCode
     */
    const val RESULT_WIFI = 9999

    /**
     * 打开设置界面 requestCode
     */
    const val RESULT_SETTING = 99999





    /**
     * 获取当前的网络状态,子线程执行
     *   0：当前网络可用
     *  1：需要网页认证的wifi
     *  2：网络不可用的状态
     *  1000：方法错误
     */
    fun ping2(): Int {
        val runtime = Runtime.getRuntime()
        try {
            val p = runtime.exec("ping -c 3 www.baidu.com")
            return p.waitFor()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 1
    }

    /**
     * 打开网络设置界面
     */
    fun openWiFiSetting(activity: Activity?, requestCode: Int = RESULT_WIFI) {
        val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
        activity?.startActivityForResult(intent, requestCode)
    }

    /**
     * 打开设置界面
     */
    fun openSetting(activity: Activity?, requestCode: Int = RESULT_SETTING) {
        val intent = Intent(Settings.ACTION_SETTINGS)
        activity?.startActivityForResult(intent, requestCode)
    }


    fun checkNetworkState(context: Context): Boolean {
        // 获取网络连接管理器
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // 需要判断版本
        // 如果当前版本大于安卓6棉花糖
        // 通过网络连接管理器获得活跃网络
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            // 是否有wifi网络
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                true
            }
            // 是否有蜂窝网络
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                true
            }
            else -> {
                false
            }
        }
    }


}