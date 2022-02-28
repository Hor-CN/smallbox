package cn.itbk.smallbox.Module.Main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cn.itbk.smallbox.APP.Base.observeEvent
import cn.itbk.smallbox.APP.Base.observeState
import cn.itbk.smallbox.APP.Base.viewBinding
import cn.itbk.smallbox.model.AppUpdateModel
import cn.itbk.smallbox.Module.Adapter.MainNavAdapter
import cn.itbk.smallbox.R
import cn.itbk.smallbox.databinding.FragmentMainBinding
import com.azhon.appupdate.config.UpdateConfiguration
import com.azhon.appupdate.manager.DownloadManager
import com.github.fragivity.finish
import com.zackratos.ultimatebarx.ultimatebarx.statusBar

/**
 * @简介 主页面Fragment
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-30
 */
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by activityViewModels()
    private val binding: FragmentMainBinding by viewBinding()
    private var lastClickTime = 0L

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
        observeViewModel()



    }

    private fun observeViewModel() {

        viewModel.viewStates.run {
            observeState(viewLifecycleOwner, MainViewState::fragments) {
//                binding.homeVp.addStatusBarTopPadding()
                binding.homeVp.isUserInputEnabled = false
                binding.homeVp.adapter = MainNavAdapter(childFragmentManager, lifecycle, it)
                binding.homeNav.setupWithViewPager2(binding.homeVp)
//                 val tabToAddBadgeAt =  binding.homeNav.tabs[1]
//                 binding.homeNav.setBadgeAtTab(tabToAddBadgeAt, AnimatedBottomBar.Badge("99"))
            }
        }

        viewModel.viewEvents.observeEvent(viewLifecycleOwner) {
            when (it) {
                is MainViewEvent.AppUpdate -> appUpdate(it.appUpdateData)
                is MainViewEvent.TabsAt -> {
                    if (it.tabAt in 0..3) {
                        binding.homeNav.selectTabAt(it.tabAt)
                    }
                }
                is MainViewEvent.StateToolbar -> {
                    activity?.statusBar {
                        transparent()
                        light = it.state
                    }
                }
            }
        }

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
            .setForcedUpgrade(it.forcedUpgrade) //设置强制更新

        DownloadManager.getInstance(activity)
            .setApkName(it.apkName)
            .setApkUrl(it.apkUrl)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setConfiguration(configuration)
            .setShowNewerToast(true)
            .setApkVersionCode(it.apkVersionCode)
            .setApkVersionName(it.apkVersionName)
            .setApkSize(it.apkSize.toString())
            .setApkDescription(it.apkDescription)
            .download()
    }

}