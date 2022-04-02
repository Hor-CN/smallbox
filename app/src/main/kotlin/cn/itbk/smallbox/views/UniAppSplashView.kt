package cn.itbk.smallbox.views

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import cn.itbk.smallbox.R
import cn.itbk.smallbox.app.SmallBoxAPP
import cn.itbk.smallbox.app.base.BaseConstant
import cn.itbk.smallbox.model.applet.AppletConfigModel
import com.bumptech.glide.Glide
import io.dcloud.feature.sdk.DCUniMPSDK
import io.dcloud.feature.sdk.Interface.IDCUniMPAppSplashView
import rxhttp.wrapper.utils.GsonUtil
import java.io.File


/**
 * @简介 闪屏页
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-03-02
 */


open class UniAppSplashView : IDCUniMPAppSplashView {

//    private lateinit var splashView: FrameLayout
    private lateinit var splashView : View

    override fun getSplashView(context: Context, appid: String, s1: String, s2: String?): View {
//        splashView = FrameLayout(context)
//        splashView.setBackgroundColor(Color.WHITE)

        val path = DCUniMPSDK.getInstance().getAppBasePath(SmallBoxAPP.instance)
        val file = File("$path/$appid/www/${BaseConstant.manifest}").run {
            GsonUtil.fromJson<AppletConfigModel>(
                readText(),
                AppletConfigModel::class.java
            ).apply {
                icon = "$path/$appid/www/static/logo.png"
            }
        }
        splashView = LayoutInflater.from(context).inflate(R.layout.splashview, null)


        Glide.with(context)
            .load(file.icon)
            .into(splashView.findViewById(R.id.splashView_imageView))

        splashView.findViewById<TextView>(R.id.splashView_textView).apply {
            text = file.name
        }
//        val imageView = ImageView(context).apply {
//            setImageResource(R.drawable.ic_author)
//        }
//        val textView = TextView(context).apply {
//            text = file.name
//            setTextColor(Color.BLACK)
//            textSize = 20f
//            gravity = Gravity.CENTER
//        }
//        val lp = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100)
////        lp.gravity = Gravity.CENTER
//        splashView.addView(imageView, lp)
//        splashView.addView(textView, lp)
        return splashView
    }

    override fun onCloseSplash(rootView: ViewGroup?) {
        rootView?.removeView(splashView)
    }
}
