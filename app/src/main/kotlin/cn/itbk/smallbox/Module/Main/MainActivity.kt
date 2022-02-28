package cn.itbk.smallbox.Module.Main

import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import cn.itbk.smallbox.APP.Base.BaseActivity
import cn.itbk.smallbox.Manager.AccountManager
import cn.itbk.smallbox.databinding.ActivityMainBinding
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