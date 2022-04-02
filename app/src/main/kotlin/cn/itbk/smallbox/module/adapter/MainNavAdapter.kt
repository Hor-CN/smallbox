package cn.itbk.smallbox.module.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @简介 首页ViewPager适配器
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-27
 */
class MainNavAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    var fragments: MutableList<Fragment>,
) : FragmentStateAdapter(fragmentManager,lifecycle) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}