package cn.itbk.smallbox.Module.Main.Store

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.itbk.smallbox.APP.Base.SingleLiveEvents
import cn.itbk.smallbox.APP.Base.asLiveData
import cn.itbk.smallbox.APP.Base.msg
import cn.itbk.smallbox.APP.Base.setState
import cn.itbk.smallbox.APP.State.PageState
import cn.itbk.smallbox.model.Srore.ListModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

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
            val listBean: MutableList<ListModel> = ArrayList<ListModel>()
            StoreRepository.loadData()
                .catch {
                    _viewStates.setState { copy(
                        pageState = PageState.Error(it.msg)
                    ) }
                }
                .collect {
                    it.goods.forEach { menu ->
                        listBean.add(ListModel(true, menu.type))
                        menu.value.forEach {  app ->
                            listBean.add(ListModel(false,app))
                        }
                    }
                    _viewStates.setState { copy(
                        pageState = PageState.Success,
                        bannerList = it.carousels,
                        contentList = listBean
                    ) }
                }
        }

    }
}