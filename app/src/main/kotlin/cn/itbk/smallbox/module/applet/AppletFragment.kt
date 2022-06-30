package cn.itbk.smallbox.module.applet

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import cn.itbk.smallbox.R
import cn.itbk.smallbox.app.base.observeEvent
import cn.itbk.smallbox.app.base.setSupportActionBar
import cn.itbk.smallbox.app.base.supportActionBar
import cn.itbk.smallbox.app.base.viewBinding
import cn.itbk.smallbox.databinding.FragmentAppletBinding
import cn.itbk.smallbox.model.applet.AppletModel
import cn.itbk.smallbox.module.main.MainViewAction
import cn.itbk.smallbox.module.main.MainViewModel
import cn.itbk.smallbox.module.main.find.FindFragment
import com.bumptech.glide.Glide
import com.github.fragivity.navigator
import com.github.fragivity.pop
import com.github.fragivity.swipeback.setEnableGesture
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import io.dcloud.feature.sdk.DCUniMPSDK
import io.dcloud.feature.unimp.config.UniMPReleaseConfiguration
import kotlin.math.abs

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-09-19
 */
class AppletFragment : Fragment(R.layout.fragment_applet) {
    private val binding: FragmentAppletBinding by viewBinding()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: AppletViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // 拦截back键事件
                    back()
                }
            })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setHasOptionsMenu(true)
        initView()
        setEnableGesture(true) // 开启SwipeBack

    }

    private fun initView() {
        val data: AppletModel = requireArguments().getSerializable("data") as AppletModel

        setSupportActionBar(binding.fragmentAppletToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true) //设置返回键可用
        binding.fragmentAppletToolbar.addStatusBarTopPadding()
        binding.fragmentAppletTop.addStatusBarTopPadding()
        binding.fragmentAppletToolbar.title = ""
        binding.fragmentAppletName.text = data.name
        binding.fragmentAppletVp2.isUserInputEnabled = false

        val tabArray = resources.getStringArray(R.array.applet_tab_arr)
        binding.fragmentAppletVp2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = tabArray.size
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> IntroduceFragment()
                    else -> FindFragment()
                }
            }
        }
        TabLayoutMediator(binding.fragmentAppletTab, binding.fragmentAppletVp2) { tab, position ->
            tab.text = tabArray[position]
        }.attach()


        binding.fragmentAppletOpen.setOnClickListener {
            if (DCUniMPSDK.getInstance().isExistsApp(data.appId)) {
                mainViewModel.dispatch(MainViewAction.OpenApplet(data.appId))
            } else {
                viewModel.dispatch(AppletViewAction.DownApplet(data.appId, data.downloadUrl))
            }
        }


        Glide.with(requireContext())
            .load(data.appIcon)
            .into(binding.fragmentAppletIv)

        binding.fragmentAppletAppbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val halfScroll = appBarLayout.totalScrollRange / 2
            val offSetAbs = abs(verticalOffset)
            val percentage: Float = if (offSetAbs < halfScroll) {
                binding.fragmentAppletToolbar.title = ""
                binding.fragmentAppletAppbar.setBackgroundColor(Color.rgb(223, 223, 224))
                binding.fragmentAppletToolbar.setBackgroundColor(Color.rgb(223, 223, 224))
                1 - offSetAbs.toFloat() / halfScroll.toFloat()
            } else {
                binding.fragmentAppletToolbar.title = data.name
                binding.fragmentAppletToolbar.setBackgroundResource(R.color.white)
                (offSetAbs - halfScroll).toFloat() / halfScroll.toFloat()
            }
            binding.fragmentAppletToolbar.alpha = percentage
        })


    }

//    private fun runApplet(appId: String) {
//        mainViewModel.dispatch(MainViewAction.OpenApplet(appId))
//    }

    private fun observeViewModel() {
        viewModel.viewEvents.observeEvent(viewLifecycleOwner) {
            when (it) {
                is AppletViewEvent.RunApplet -> {
//                    val un = UniMPReleaseConfiguration()
//                    un.wgtPath = it.downUrl
//                    un.password = ""
//                    DCUniMPSDK.getInstance().releaseWgtToRunPath(it.appId, un) { i, _ ->
//                        if (i == 1) {
//                            runApplet(it.appId)
//                        } else {
//                            Log.d("TAG", "observeViewModel: 解压失败")
//                        }
//                    }
                    mainViewModel.dispatch(MainViewAction.UnWgt(it.appId,it.downUrl,true))
                }
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            back()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun back() {
        navigator.pop()
    }

}