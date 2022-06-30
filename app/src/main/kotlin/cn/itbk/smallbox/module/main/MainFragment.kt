package cn.itbk.smallbox.module.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cn.itbk.smallbox.R
import cn.itbk.smallbox.app.base.observeEvent
import cn.itbk.smallbox.app.base.observeState
import cn.itbk.smallbox.app.base.viewBinding
import cn.itbk.smallbox.databinding.FragmentMainBinding
import cn.itbk.smallbox.model.AppUpdateModel
import cn.itbk.smallbox.module.adapter.MainNavAdapter
import cn.itbk.smallbox.module.main.home.HomeViewAction
import cn.itbk.smallbox.module.main.home.HomeViewModel
import cn.itbk.smallbox.views.UniAppSplashView
import com.azhon.appupdate.config.UpdateConfiguration
import com.azhon.appupdate.manager.DownloadManager
import com.github.fragivity.finish
import com.kongzue.dialogx.dialogs.PopTip
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import io.dcloud.feature.sdk.DCUniMPSDK
import io.dcloud.feature.sdk.Interface.IUniMP
import io.dcloud.feature.unimp.config.UniMPOpenConfiguration
import io.dcloud.feature.unimp.config.UniMPReleaseConfiguration
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


/**
 * @简介 主页面Fragment
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-30
 */
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val binding: FragmentMainBinding by viewBinding()
    private var lastClickTime = 0L

    /** uniMp小程序实例缓存**/
    private var uniMpCaches: HashMap<String, IUniMP> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher
            .addCallback(this) {
                val current = System.currentTimeMillis()
                if (current - lastClickTime > 2000) {
                    lastClickTime = current
                } else {
                    finish()
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUniMp()
        viewModel.viewStates.run {
            observeState(viewLifecycleOwner, MainViewState::fragments) {
                binding.homeVp.isUserInputEnabled = false
                binding.homeVp.adapter = MainNavAdapter(childFragmentManager, lifecycle, it)
                binding.homeNav.setupWithViewPager2(binding.homeVp)
                // val tabToAddBadgeAt =  binding.homeNav.tabs[1]
                // binding.homeNav.setBadgeAtTab(tabToAddBadgeAt, AnimatedBottomBar.Badge("99"))
            }

        }

        viewModel.viewEvents.observeEvent(viewLifecycleOwner) {
            when (it) {
                is MainViewEvent.AppUpdate -> appUpdate(it.appUpdateData)
                is MainViewEvent.TabsAt -> selectTabAt(it.tabAt)
                is MainViewEvent.StateToolbar -> stateToolbar(it.state)
                is MainViewEvent.MotionToast -> motionToast(
                    it.title,
                    it.motionToastStyle,
                    it.message,
                )
                is MainViewEvent.UnWgt -> unWgt(it.name, it.filePath,it.is_run)
                is MainViewEvent.OpenApplet -> openApplet(it.appId)
            }
        }

    }


    private fun initUniMp() {
        // X键
        DCUniMPSDK.getInstance().setCapsuleCloseButtonClickCallBack { appId ->
            if (uniMpCaches.containsKey(appId)) {
                val mIUniMP: IUniMP? = uniMpCaches[appId]
                if (mIUniMP != null && mIUniMP.isRuning) {
                    mIUniMP.closeUniMP()
                    uniMpCaches.remove(appId)
                }
            }
        }

//        DCUniMPSDK.getInstance().setCapsuleMenuButtonClickCallBack { appid ->
//            val intent = Intent(context, MenuDialogActivity::class.java) //跳转宿主构建的activity
//            DCUniMPSDK.getInstance().startActivityForUniMPTask(
//                appid,
//                intent
//            ) //通过startActivityForUniMPTask启动宿主activity。运行在小程序activity堆栈中
//        }
    }

    private fun unWgt(name: String, filePath: String,is_run: Boolean) {
        Log.d("小程序:", "解压名称:${name},路径${filePath}")
        val un = UniMPReleaseConfiguration()
        un.wgtPath = filePath
        un.password = ""
        DCUniMPSDK.getInstance().releaseWgtToRunPath(name, un) { i, _ ->
            if (i == 1) {
                homeViewModel.dispatch(HomeViewAction.LoadApplets)
                if (is_run) {
                    openApplet(name)
                }else {
                    motionToast(
                        title = "导入成功",
                        motionToastStyle = MotionToastStyle.SUCCESS,
                        message = "${name}拓展导入成功!"
                    )
                }
            }else{
                motionToast(
                    title = "导入失败",
                    motionToastStyle = MotionToastStyle.ERROR,
                    message = "${name}文件错误或已损坏!"
                )
            }
        }
    }


    private fun openApplet(appId: String) {
        val uniMPOpenConfiguration = UniMPOpenConfiguration().apply {
            splashClass = UniAppSplashView::class.java
        }
        val iUniMP =
            DCUniMPSDK.getInstance().openUniMP(requireActivity(), appId, uniMPOpenConfiguration)
        uniMpCaches[appId] = iUniMP
    }

    private fun appUpdate(it: AppUpdateModel) {

        val configuration = UpdateConfiguration()
            .setEnableLog(true) //输出错误日志
            //.setHttpManager() 设置自定义的下载
            .setJumpInstallPage(true) //下载完成自动跳动安装页面
//                    .setDialogImage(R.drawable.dialog_update) //设置对话框背景图片 (图片规范参照demo中的示例图)
            //.setDialogButtonColor(Color.parseColor("#E743DA")) //设置按钮的颜色
            //.setDialogProgressBarColor(Color.parseColor("#E743DA"))//设置对话框强制更新时进度条和文字的颜色
            .setDialogButtonTextColor(Color.WHITE) //设置按钮的文字颜色
            .setShowNotification(true)//设置是否显示通知栏进度
            .setShowBgdToast(false)//设置是否提示后台下载toast
            .setForcedUpgrade(it.forcedupgrade) //设置强制更新

        DownloadManager.getInstance(activity)
            .setApkName(it.apkname)
            .setApkUrl(it.apkurl)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setConfiguration(configuration)
            .setShowNewerToast(true)
            .setApkVersionCode(it.apkversioncode)
            .setApkVersionName(it.apkversionname)
            .setApkSize(it.apksize.toString())
            .setApkDescription(it.apkdescription)
            .download()
    }

    private fun motionToast(title: String, motionToastStyle: MotionToastStyle, message: String) {
        MotionToast.createColorToast(
            requireActivity(),
            title,
            message,
            motionToastStyle,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            null
        )
    }

    private fun selectTabAt(tabAt: Int) {
        if (tabAt in 0..3) {
            binding.homeNav.selectTabAt(tabAt)
        }
    }

    private fun stateToolbar(state: Boolean) {
        activity?.statusBar {
            transparent()
            light = state
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        uniMpCaches.clear()
    }
}