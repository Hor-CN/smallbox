package cn.itbk.smallbox.module.search

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cn.itbk.smallbox.app.base.viewBinding
import cn.itbk.smallbox.module.main.MainViewModel
import cn.itbk.smallbox.R
import cn.itbk.smallbox.databinding.FragmentSearchBinding
import com.github.fragivity.navigator
import com.github.fragivity.pop
import com.github.fragivity.swipeback.setEnableGesture
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding

import com.kongzue.dialogx.dialogs.PopTip
import com.kongzue.dialogx.dialogs.MessageDialog

import com.kongzue.dialogx.util.TextInfo


/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-11-21
 */
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding: FragmentSearchBinding by viewBinding()
    private val mainViewModel: MainViewModel by activityViewModels()

    private var isDeleteHistoryLabel = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback( this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // 拦截back键事件
                    back()
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setEnableGesture(true) // 开启SwipeBack

    }

    private fun initView() {
        // toolbar 头部padding
        binding.fragmentSearchToolbar.addStatusBarTopPadding()
        // toolbar 返回键
        binding.fragmentSearchBack.setOnClickListener {
            back()
        }
        // 历史搜索删除
        binding.fragmentSearchDelete.setOnClickListener {
            binding.fragmentSearchHistoryLabelView.isDeleteButton = true
            isDeleteHistoryLabel = true
            it.visibility = View.GONE
            binding.fragmentSearchDeleteFlex.visibility = View.VISIBLE
        }
        // 历史删除点击后
        binding.fragmentSearchDeleteFlexComplete.setOnClickListener {
            binding.fragmentSearchHistoryLabelView.isDeleteButton = false
            isDeleteHistoryLabel = false
            binding.fragmentSearchDeleteFlex.visibility = View.GONE
            binding.fragmentSearchDelete.visibility = View.VISIBLE
        }
        // 历史记录label
        binding.fragmentSearchHistoryLabelView.setOnLabelClickListener { index, v, s ->
            if (binding.fragmentSearchHistoryLabelView.isDeleteButton) {      //是否开启了删除模式
                //删除标签
                binding.fragmentSearchHistoryLabelView.remove(index);
            } else {
                PopTip.show(s)
            }
        }
        // 删除所有历史记录
        binding.fragmentSearchDeleteFlexAll.setOnClickListener {
            MessageDialog.show(getString(R.string.tip), getString(R.string.history_delete), getString(R.string.delete),getString(R.string.cancel))
                .setOkTextInfo(TextInfo().setFontColor(Color.RED))
                .setOkButton { _, _ ->
                    binding.fragmentSearchHistoryLabelView.removeAllViews()
                    binding.fragmentSearchHistoryLabelView.isDeleteButton = false
                    isDeleteHistoryLabel = false
                    binding.fragmentSearchDeleteFlex.visibility = View.GONE
                    binding.fragmentSearchDelete.visibility = View.VISIBLE
                    false
                }

        }
        // 监听软键盘搜索键
        binding.fragmentSearchEdit.setOnEditorActionListener{ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                PopTip.show(v.text)
            }
            false
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