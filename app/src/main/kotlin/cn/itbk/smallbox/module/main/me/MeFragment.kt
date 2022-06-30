package cn.itbk.smallbox.module.main.me

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cn.itbk.smallbox.R
import cn.itbk.smallbox.app.SmallBoxAPP.Companion.mTencent
import cn.itbk.smallbox.app.base.observeEvent
import cn.itbk.smallbox.app.base.observeState
import cn.itbk.smallbox.app.base.viewBinding
import cn.itbk.smallbox.app.state.ErrorState
import cn.itbk.smallbox.app.state.LoadingState
import cn.itbk.smallbox.app.state.PageState
import cn.itbk.smallbox.databinding.FragmentMeBinding
import cn.itbk.smallbox.module.main.MainViewModel
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.kongzue.dialogx.dialogs.FullScreenDialog
import com.kongzue.dialogx.dialogs.PopTip
import com.kongzue.dialogx.interfaces.OnBindView
import com.tencent.connect.common.Constants
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zy.multistatepage.MultiStateContainer
import com.zy.multistatepage.bindMultiState
import com.zy.multistatepage.state.SuccessState
import org.json.JSONObject

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
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var dialog: FullScreenDialog
    private val iu = object : IUiListener {
        override fun onComplete(response: Any?) {
            val jsonResp = response as JSONObject
            Log.d("QQ登录1", "${ jsonResp["access_token"]} ")
            meViewModel.dispatch(MeViewAction.QQLogin(jsonResp["access_token"].toString()))
            mTencent.setAccessToken(jsonResp["access_token"].toString(), jsonResp["expires_in"].toString())
            mTencent.openId = jsonResp["openid"].toString()
        }

        override fun onError(error: UiError?) {
            Log.d("QQ登录", "onError: 错误 $error")
        }

        override fun onCancel() {
            Log.d("QQ登录", "取消")
        }

        override fun onWarning(i: Int) {
            Log.d("QQ登录", "onWarning: $i")
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //腾讯QQ回调
        Tencent.onActivityResultData(requestCode, resultCode, data,iu)
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, iu)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        multiState = binding.fragmentMeLl.bindMultiState()
        binding.fragmentMeLl.addStatusBarTopPadding()

    }

    private fun observeViewModel() {

        meViewModel.viewStates.run {
            observeState(viewLifecycleOwner, MeViewState::User) {
                if(it != null) {
                    meViewModel.dispatch(MeViewAction.CurrentUser)
                    binding.fragmentMeUsername.text = it.nikeName
                    binding.fragmentMeUsername2.text = it.nikeName
                    binding.fragmentMeDeveloper.isVisible = it.developer
                    binding.fragmentMeIntroduce.text = it.introduce
                    Glide.with(requireContext())
                        .load(it.avatarImg)
                        .placeholder(R.drawable.ic_author) //加载未完成时显示占位图
                        .into(binding.fragmentMeImg)
                    Glide.with(requireContext())
                        .load(it.avatarImg)
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
                    v.findViewById<ImageView>(R.id.login_qq).setOnClickListener {
                        Tencent.setIsPermissionGranted(true)
                        if (!mTencent.isSessionValid)
                        {
                            when ( mTencent.login(requireActivity(),"all",iu,true)) {
                                0 -> PopTip.show("正常登录")
                                1 -> PopTip.show("开始登录")
                                -1 -> {
                                    PopTip.show("异常")
                                    mTencent.logout(requireActivity())
                                }
                                2 -> PopTip.show("使用H5登陆或显示下载页面")
                                else -> PopTip.show("出错")
                            }
                        }

                    }

                }
            })
    }

}