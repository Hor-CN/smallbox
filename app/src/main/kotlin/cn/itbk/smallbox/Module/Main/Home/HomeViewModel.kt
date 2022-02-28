package cn.itbk.smallbox.Module.Main.Home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.itbk.smallbox.APP.Base.SingleLiveEvents
import cn.itbk.smallbox.APP.Base.asLiveData
import cn.itbk.smallbox.APP.Base.setState
import cn.itbk.smallbox.APP.State.PageState
import cn.itbk.smallbox.R
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @简介 Home页面的ViewModel
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-29
 */
class HomeViewModel : ViewModel() {

    private val _viewStates = MutableLiveData(HomeViewState())
    val viewStates = _viewStates.asLiveData()
    private val _viewEvents: SingleLiveEvents<HomeViewEvent> = SingleLiveEvents()
    val viewEvents = _viewEvents.asLiveData()

    init {
        loadApplets()
    }

    fun dispatch(viewAction: HomeViewAction) {
        when (viewAction) {
            is HomeViewAction.LoadApplets -> loadApplets()
            is HomeViewAction.OnRefresh -> loadApplets()
            is HomeViewAction.DeleteApplet -> deleteApplet(viewAction.path)
        }
    }

    /**
     * 加载小程序列表
     */
    private fun loadApplets() {
        viewModelScope.launch {
            HomeRepository.fetchApplets().catch {
                _viewStates.setState {
                    copy(pageState = PageState.Error(R.string.error_msg))
                }
            }.collect {
                if (it.isNotEmpty()) {
                    _viewStates.setState {
                        copy(appletList = it, pageState = PageState.Success)
                    }
                } else {
                    _viewStates.setState {
                        copy(pageState = PageState.Empty(R.string.no_applet))
                    }
                }
            }
        }
    }

    /**
     * 删除插件
     */
    private fun deleteApplet(path: String) {
        viewModelScope.launch {
            HomeRepository.deleteApplet(path)
        }
    }

}