package cn.itbk.smallbox.module.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import cn.itbk.smallbox.app.base.BaseActivity
import cn.itbk.smallbox.databinding.ActivityMainBinding
import cn.itbk.smallbox.module.main.me.MeViewModel
import com.github.fragivity.loadRoot
import com.zackratos.ultimatebarx.ultimatebarx.statusBarOnly


/**
 * @简介 主界面Activity
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-26
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {
//    private val viewModel: MainViewModel by viewModels()
    private val meViewModel : MeViewModel by viewModels()
    override fun initViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.navHost.id) as NavHostFragment
        // 主页面Fragment
        navHostFragment.loadRoot { MainFragment() }

        statusBarOnly  {
            fitWindow = false
            light = true
        }
    }

}