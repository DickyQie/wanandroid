package com.zhangqie.wanandroid.ui.modular.ui.mvp

import com.zhangqie.wanandroid.api.SubscriberCallBack
import com.zhangqie.wanandroid.bean.ModularListInfo
import com.zhangqie.wanandroid.callback.ICallBack
import com.zhangqie.wanandroid.model.BaseModel
import io.reactivex.disposables.Disposable

/**
 * Created by zhangqie on 2019/2/21
 * Describe:
 */
class ModularTabModel(iCallBack: ICallBack<Any>) : BaseModel<ICallBack<Any>>() {

    init {
        attachModel(iCallBack)
    }

    fun showRequestProjectDataList(page: Int,cid: Int){
        addSubscription(jsonApiService.getModularListData(page,cid),object : SubscriberCallBack<ModularListInfo>(){

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }

            override fun onSuccess(result: ModularListInfo?) {
                listener.onSuccess(result)
            }

            override fun onFailure(errorMsg: String?) {
                listener.onFailure(errorMsg)
            }

        })
    }

}