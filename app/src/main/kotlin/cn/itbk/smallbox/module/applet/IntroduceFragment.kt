package cn.itbk.smallbox.module.applet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.itbk.smallbox.app.base.viewBinding
import cn.itbk.smallbox.R
import cn.itbk.smallbox.databinding.FragmentAppletIntroduceBinding
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-02-23
 */
class IntroduceFragment : Fragment(R.layout.fragment_applet_introduce) {
    private val binding: FragmentAppletIntroduceBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()



    }

    private fun initView() {
        val data = ArrayList<String>()
        for (i in 0..2) {
            data.add("text-$i")
        }
        binding.fragmentAppletIntroduceRv.linear(RecyclerView.HORIZONTAL).setup {
            addType<String>(R.layout.item_applet_introduce_rv_item)
        }.models = data
    }




}