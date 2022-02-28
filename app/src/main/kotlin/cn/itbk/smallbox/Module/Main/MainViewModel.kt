package cn.itbk.smallbox.Module.Main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.itbk.smallbox.APP.Base.SingleLiveEvents
import cn.itbk.smallbox.APP.Base.asLiveData
import cn.itbk.smallbox.APP.Base.setEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @简介 MainActivity ViewModel
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-30
 */
class MainViewModel : ViewModel() {

    private val _viewStates = MutableLiveData(MainViewState())
    val viewStates = _viewStates.asLiveData()
    private val _viewEvents: SingleLiveEvents<MainViewEvent> = SingleLiveEvents()
    val viewEvents = _viewEvents.asLiveData()

    init {
        dispatch(MainViewAction.AppUpdate)
    }

    fun dispatch(viewAction: MainViewAction) {
        when (viewAction) {
            is MainViewAction.AppUpdate -> appUpdate()
            is MainViewAction.TabsAt -> tabsAt(viewAction.tabAt)
            is MainViewAction.StateToolbar -> stateToolbar(viewAction.state)
        }
    }

//    fun isLogin() : Boolean{
//        if (_viewStates.value?.User == null) {
//            return false
//        }
//    }

    // 切换界面
    private fun tabsAt(i: Int) {
        _viewEvents.setEvent(MainViewEvent.TabsAt(i))
    }

    // 状态栏
    private fun stateToolbar(state: Boolean) {
        _viewEvents.setEvent(MainViewEvent.StateToolbar(state))
    }

    // 应用更新
    private fun appUpdate() {
        viewModelScope.launch {
            MainRepository.appUpdate().collect {
                _viewEvents.setEvent(MainViewEvent.AppUpdate(it))
            }
        }
    }

}