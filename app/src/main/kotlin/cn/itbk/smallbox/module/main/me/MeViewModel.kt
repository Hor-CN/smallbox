package cn.itbk.smallbox.module.main.me

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.itbk.smallbox.app.base.*
import cn.itbk.smallbox.app.state.PageState
import cn.itbk.smallbox.manager.AccountManager
import cn.itbk.smallbox.module.main.MainRepository
import com.kongzue.dialogx.dialogs.PopTip
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @简介 我的
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-09-13
 */
class MeViewModel : ViewModel() {

    private val _viewStates = MutableLiveData(MeViewState())
    val viewStates = _viewStates.asLiveData()
    private val _viewEvents: SingleLiveEvents<MeViewEvent> = SingleLiveEvents()
    val viewEvents = _viewEvents.asLiveData()

    init {
        dispatch(MeViewAction.CurrentUser)
    }

    fun dispatch(viewAction: MeViewAction) {
        when (viewAction) {
            // 获取当前登录用户信息
            is MeViewAction.CurrentUser -> getCurrentUser()
            // 登录Login
            is MeViewAction.Login -> login(viewAction.username,viewAction.password)
//            is MeViewAction.QQLogin -> qqLogin(viewAction.access_token)
        }
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            if (AccountManager.isLogin) {
                _viewStates.setState {
                    // 登录成功
                    copy(pageState = PageState.Success)
                }
            } else {
                _viewStates.setState {
                    copy(pageState = PageState.Error("您还没有登录呢"))
                }
            }
        }
    }

    private fun login(username:String,password:String) {
        viewModelScope.launch {
            MeRepository.login(username, password)
                .catch {
                    PopTip.show(it.msg)
                }
                .collect {
                AccountManager.signIn(it)
                _viewEvents.setEvent(MeViewEvent.DismissDialog)
                _viewStates.setState {
                    copy(User = AccountManager.accountInfo, pageState = PageState.Success)
                }
            }
        }
    }

    private fun qqLogin(access_token: String) {
        Log.d("登录login", "qqLogin: $access_token")
        viewModelScope.launch {
            MeRepository.qqLogin(access_token).catch {
                PopTip.show(it.msg)
            }.collect {
                Log.d("登录", "qqLogin: $it")
                AccountManager.signIn(it)
                //  _viewEvents.setEvent(MeViewEvent.DismissDialog)
                _viewStates.setState {
                    copy(User = AccountManager.accountInfo)
                }
            }
        }
    }



}