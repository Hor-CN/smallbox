package cn.itbk.smallbox.app.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import cn.itbk.smallbox.databinding.StateLoadingBinding
import com.zy.multistatepage.MultiState
import com.zy.multistatepage.MultiStateContainer

/**
 * @简介 页面加载状态
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-28
 */
class LoadingState : MultiState() {

    private lateinit var binding: StateLoadingBinding

    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        binding = StateLoadingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onMultiStateViewCreate(view: View) {

    }

}
