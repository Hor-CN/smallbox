package cn.itbk.smallbox.module.main.find

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import cn.itbk.smallbox.app.base.viewBinding
import cn.itbk.smallbox.app.state.EmptyState
import cn.itbk.smallbox.R
import cn.itbk.smallbox.databinding.FragmentFindBinding
import com.zy.multistatepage.MultiStateContainer
import com.zy.multistatepage.bindMultiState
import io.dcloud.feature.sdk.Interface.IUniMP
import java.util.*


/**
 * @简介 发现
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-27
 */
class FindFragment : Fragment(R.layout.fragment_find) {
    private lateinit var multiState: MultiStateContainer
    private val binding: FragmentFindBinding by viewBinding()
    var mUniMPCaches = HashMap<String, IUniMP>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        multiState = binding.ll.bindMultiState()
        showEmpty()
        initView()
    }

    private fun initView() {
//        binding.button1.setOnClickListener {
//            navigator.push(InfoFragment::class) {
//                applySlideInOut()
//            }
//        }



    }

    private fun showEmpty() {
        multiState.show<EmptyState> {
            it.setEmptyMsg("正在开发中，请等待")
        }
    }


}