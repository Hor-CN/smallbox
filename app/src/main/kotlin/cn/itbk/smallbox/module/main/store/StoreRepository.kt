package cn.itbk.smallbox.module.main.store

import cn.itbk.smallbox.app.base.BaseConstant
import cn.itbk.smallbox.model.store.ItemList
import kotlinx.coroutines.flow.Flow
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse

/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-09-09
 */
object StoreRepository {


     fun loadData(): Flow<ItemList> {
        return RxHttp.get(BaseConstant.store_index)
            .connectTimeout(3000)
            .toFlowResponse()
    }


}