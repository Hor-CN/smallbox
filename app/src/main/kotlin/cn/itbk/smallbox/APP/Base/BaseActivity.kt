package cn.itbk.smallbox.APP.Base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * @简介 Activity基类
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-26
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    // 管理 ViewBinding
    private lateinit var _viewBinding: VB

    /**
     * 设置 ViewBinding
     * 使用时直接使用binding.
     */
    protected abstract fun initViewBinding(): VB

    // 获取 ViewBinding
    protected val binding get() = _viewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewBinding = initViewBinding()
        setContentView(_viewBinding.root)


    }


}