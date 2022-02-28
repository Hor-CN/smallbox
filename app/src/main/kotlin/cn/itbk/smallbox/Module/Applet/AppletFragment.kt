package cn.itbk.smallbox.Module.Applet

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import cn.itbk.smallbox.APP.Base.setSupportActionBar
import cn.itbk.smallbox.APP.Base.supportActionBar
import cn.itbk.smallbox.APP.Base.viewBinding
import cn.itbk.smallbox.model.applet.AppletModel
import cn.itbk.smallbox.Module.Main.Find.FindFragment
import cn.itbk.smallbox.Module.Main.MainViewModel
import cn.itbk.smallbox.R
import cn.itbk.smallbox.databinding.FragmentAppletBinding
import com.github.fragivity.navigator
import com.github.fragivity.pop
import com.github.fragivity.swipeback.setEnableGesture
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
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
                return when(position) {
                    0 -> IntroduceFragment()
                    else -> FindFragment()
                }
            }
        }
        TabLayoutMediator(binding.fragmentAppletTab, binding.fragmentAppletVp2) { tab, position ->
            tab.text = tabArray[position]
        }.attach()


        binding.fragmentAppletOpen.setOnClickListener {

        }



        binding.fragmentAppletAppbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val halfScroll = appBarLayout.totalScrollRange / 2
            val offSetAbs = abs(verticalOffset)
            val percentage: Float = if (offSetAbs < halfScroll) {
                binding.fragmentAppletToolbar.title = ""
                binding.fragmentAppletAppbar.setBackgroundColor(Color.rgb(223,223,224))
                binding.fragmentAppletToolbar.setBackgroundColor(Color.rgb(223,223,224))
                1 - offSetAbs.toFloat() / halfScroll.toFloat()
            } else {
                binding.fragmentAppletToolbar.title = data.name
                binding.fragmentAppletToolbar.setBackgroundResource(R.color.white)

                (offSetAbs - halfScroll).toFloat() / halfScroll.toFloat()
            }
            binding.fragmentAppletToolbar.alpha = percentage
        })


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