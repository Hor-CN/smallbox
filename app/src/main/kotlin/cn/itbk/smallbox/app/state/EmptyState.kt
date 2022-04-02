package cn.itbk.smallbox.app.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import cn.itbk.smallbox.R
import cn.itbk.smallbox.databinding.StateEmptyBinding
import com.zy.multistatepage.MultiState
import com.zy.multistatepage.MultiStateContainer
import com.zy.multistatepage.state.ErrorState

/**
 * @简介 空页面状态
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-28
 */
class EmptyState : MultiState() {

    private lateinit var binding:StateEmptyBinding
    private lateinit var context:Context
    private var retry: ErrorState.OnRetryClickListener? = null

    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        binding = StateEmptyBinding.inflate(inflater,container,false)
        this.context = context
        return binding.root
    }

    override fun onMultiStateViewCreate(view: View) {
        binding.btEmptyRetry.setOnClickListener { retry?.retry() }
        binding.btEmptyRetry.visibility  = View.GONE // 隐藏按钮

    }

    fun setEmptyMsg(msg : String) {
        binding.tvEmptyMsg.text = msg
    }
    fun setEmptyMsg(msg: Int) {
        binding.tvEmptyMsg.text = context.getString(msg)
    }


    fun retry(text:String? = context.getString(R.string.retry), retry: ErrorState.OnRetryClickListener) {
        binding.btEmptyRetry.text = text
        binding.btEmptyRetry.visibility  = View.VISIBLE // 显示按钮
        this.retry = retry
    }

    fun interface OnRetryClickListener {
        fun retry()
    }

}