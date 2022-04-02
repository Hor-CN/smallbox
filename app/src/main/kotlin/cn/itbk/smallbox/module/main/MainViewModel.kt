package cn.itbk.smallbox.module.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.itbk.smallbox.app.base.SingleLiveEvents
import cn.itbk.smallbox.app.base.asLiveData
import cn.itbk.smallbox.app.base.msg
import cn.itbk.smallbox.app.base.setEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import www.sanju.motiontoast.MotionToastStyle

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
        appUpdate()
    }

    fun dispatch(viewAction: MainViewAction) {
        when (viewAction) {
            // 应用更新
            is MainViewAction.AppUpdate -> appUpdate()
            // 切换界面
            is MainViewAction.TabsAt -> _viewEvents.setEvent(MainViewEvent.TabsAt(viewAction.tabAt))
            // 状态栏
            is MainViewAction.StateToolbar -> _viewEvents.setEvent(
                MainViewEvent.StateToolbar(
                    viewAction.state
                )
            )
            // 提示弹窗
            is MainViewAction.MotionToast -> motionToast(
                viewAction.motionToastStyle,
                viewAction.title,
                viewAction.message
            )
            // 解压小程序
            is MainViewAction.UnWgt -> _viewEvents.setEvent(MainViewEvent.UnWgt(viewAction.name,viewAction.filePath))
            // 打开小程序
            is MainViewAction.OpenApplet -> _viewEvents.setEvent(MainViewEvent.OpenApplet(viewAction.appId))

        }
    }


    private fun motionToast(motionToastStyle: MotionToastStyle, title: String, msg: String) {
        _viewEvents.setEvent(MainViewEvent.MotionToast(motionToastStyle, title, msg))
    }

    // 应用更新
    private fun appUpdate() {
        viewModelScope.launch {
            MainRepository.appUpdate()
                .catch {
                    motionToast(
                        title = "",
                        motionToastStyle = MotionToastStyle.WARNING,
                        msg = it.msg
                    )
                }.collect {
                    _viewEvents.setEvent(MainViewEvent.AppUpdate(it))
                }
        }
    }

}