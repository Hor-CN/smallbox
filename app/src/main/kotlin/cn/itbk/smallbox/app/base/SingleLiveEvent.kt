package cn.itbk.smallbox.app.base

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-09-09
 */

class SingleLiveEvent<T> : MutableLiveData<T>() {

    companion object {
        private const val TAG = "SingleLiveEvent"
    }

    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(@NonNull owner: LifecycleOwner, @NonNull observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(TAG, "已注册多个观察员，但只有一个观察员会收到更改通知。")
        }

        // Observe the internal MutableLiveData
        super.observe(owner) { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }

    /**
     * 用于T为空或单位的情况，使呼叫更干净。
     */
    @MainThread
    fun call() {
        value = null
    }
}