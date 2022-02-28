package cn.itbk.smallbox.Module.Main.Me

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.itbk.smallbox.APP.Base.SingleLiveEvents
import cn.itbk.smallbox.APP.Base.asLiveData
import cn.itbk.smallbox.APP.Base.setEvent
import cn.itbk.smallbox.APP.Base.setState
import cn.itbk.smallbox.APP.State.PageState
import cn.itbk.smallbox.Manager.AccountManager
import cn.itbk.smallbox.R
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
                    copy(pageState = PageState.Error(R.string.not_login))
                }
            }
        }
    }

    private fun login(username:String,password:String) {
        viewModelScope.launch {
            MeRepository.login(username, password).collect {
                AccountManager.signIn(it)
                _viewEvents.setEvent(MeViewEvent.DismissDialog)
                _viewStates.setState {
                    copy(User = AccountManager.accountInfo, pageState = PageState.Success)
                }
            }
        }
    }

}