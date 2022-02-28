package cn.itbk.smallbox.Module.Applet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2022-02-25
 */
class AppletViewModel : ViewModel() {

    fun downApplet(downUrl: String) {
        viewModelScope.launch {
            AppletRepository.downApplet(downUrl)
                .catch {

                }
                .collect {

                }
        }
    }

}