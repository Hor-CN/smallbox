package cn.itbk.smallbox.module.main.me

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cn.itbk.smallbox.app.base.observeEvent
import cn.itbk.smallbox.app.base.observeState
import cn.itbk.smallbox.app.base.viewBinding
import cn.itbk.smallbox.app.state.ErrorState
import cn.itbk.smallbox.app.state.LoadingState
import cn.itbk.smallbox.app.state.PageState
import cn.itbk.smallbox.R
import cn.itbk.smallbox.databinding.FragmentMeBinding
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.kongzue.dialogx.dialogs.FullScreenDialog
import com.kongzue.dialogx.dialogs.PopTip
import com.kongzue.dialogx.interfaces.OnBindView
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zy.multistatepage.MultiStateContainer
import com.zy.multistatepage.bindMultiState
import com.zy.multistatepage.state.SuccessState

/**
 * @简介 我的
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-27
 */
class MeFragment : Fragment(R.layout.fragment_me) {
    private lateinit var multiState: MultiStateContainer
    private val binding: FragmentMeBinding by viewBinding()
    private val meViewModel: MeViewModel by activityViewModels()
    private lateinit var dialog: FullScreenDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        multiState = binding.fragmentMeLl.bindMultiState()
        binding.fragmentMeLl.addStatusBarTopPadding()
    }

    private fun observeViewModel() {
        meViewModel.viewStates.run {
            observeState(viewLifecycleOwner, MeViewState::User) {
                if (it != null) {
                    binding.fragmentMeUsername.text = it.nickName
                    binding.fragmentMeUsername2.text = it.nickName
//                    binding.fragmentMeDeveloper.isVisible = it.developer
                    binding.fragmentMeIntroduce.text = it.introduce
                    Glide.with(requireContext())
                        .load(it.avatar)
                        .placeholder(R.drawable.ic_author) //加载未完成时显示占位图
                        .into(binding.fragmentMeImg)
                    Glide.with(requireContext())
                        .load(it.avatar)
                        .placeholder(R.drawable.ic_author) //加载未完成时显示占位图
                        .into(binding.fragmentMeTx2)
                }
            }
            observeState(viewLifecycleOwner, MeViewState::pageState) {
                when(it){
                    is PageState.Loading -> multiState.show(LoadingState())
                    is PageState.Success -> multiState.show(SuccessState())
                    is PageState.Error -> multiState.show<ErrorState> { state ->
                        state.setEmptyMsg(it.message)
                        state.retry(getString(R.string.login)) {
                            showLoginDialog()
                        }
                    }
                }
            }
        }
        meViewModel.viewEvents.observeEvent(viewLifecycleOwner) {
            when (it) {
                is MeViewEvent.ToastEvent -> {
                    PopTip.show(it.int)
                }
                is MeViewEvent.DismissDialog -> dialog.dismiss()
            }
        }
    }

    private fun showLoginDialog() {
        dialog =
            FullScreenDialog.show(object : OnBindView<FullScreenDialog>(R.layout.fragment_login) {
                override fun onBind(dialog: FullScreenDialog?, v: View) {
                    val edUserName: TextInputLayout = v.findViewById(R.id.login_ed_name)
                    val edPassword: TextInputLayout = v.findViewById(R.id.login_ed_password)
                    v.findViewById<TextView>(R.id.login_btn_cancel).setOnClickListener {
                        dialog?.dismiss()
                    }
                    v.findViewById<AppCompatButton>(R.id.login_btn_login).setOnClickListener {
                        meViewModel.dispatch(
                            MeViewAction.Login(
                                edUserName.editText?.text.toString(),
                                edPassword.editText?.text.toString()
                            )
                        )
                    }
                }
            })
    }

}