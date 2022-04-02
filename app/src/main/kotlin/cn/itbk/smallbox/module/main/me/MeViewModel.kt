package cn.itbk.smallbox.module.main.me

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.itbk.smallbox.app.base.*
import cn.itbk.smallbox.app.state.PageState
import cn.itbk.smallbox.manager.AccountManager
import com.kongzue.dialogx.dialogs.PopTip
import io.dcloud.common.util.Md5Utils
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
            is MeViewAction.CurrentUser -> getCurrentUser()
            is MeViewAction.Login -> login(viewAction.username,viewAction.password)
        }
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            if (AccountManager.isLogin) {
                _viewStates.setState {
                    copy(User = AccountManager.accountInfo, pageState = PageState.Success)
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

}