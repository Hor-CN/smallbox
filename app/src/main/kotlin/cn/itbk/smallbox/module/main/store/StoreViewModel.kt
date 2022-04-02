package cn.itbk.smallbox.module.main.store

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.itbk.smallbox.R
import cn.itbk.smallbox.app.base.SingleLiveEvents
import cn.itbk.smallbox.app.base.asLiveData
import cn.itbk.smallbox.app.base.msg
import cn.itbk.smallbox.app.base.setState
import cn.itbk.smallbox.app.state.PageState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @简介 商城viewModel
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-09-07
 */
class StoreViewModel : ViewModel() {

    private val _viewStates = MutableLiveData(StoreViewState())
    val viewStates = _viewStates.asLiveData()
    private val _viewEvents: SingleLiveEvents<StoreViewEvent> = SingleLiveEvents()
    val viewEvents = _viewEvents.asLiveData()

    init {
        loadData()
    }

    fun dispatch(viewAction: StoreViewAction) {
        when (viewAction) {
            is StoreViewAction.LoadApplets -> loadData()
        }
    }


    private fun loadData() {
        viewModelScope.launch {
            StoreRepository.loadData()
                .catch {
                    _viewStates.setState { copy(
                        pageState = PageState.Error(it.msg)
                    ) }
                }
                .collect {
                    _viewStates.setState { copy(
                        pageState = PageState.Success,
                        bannerList = it.carousels,
                        contentList = it.goods
                    ) }
                }
        }

    }
}