package com.zhangqie.wanandroid.ui.navigation

import android.util.Log
import com.zhangqie.wanandroid.api.SubscriberCallBack
import com.zhangqie.wanandroid.bean.NavigationInfo
import com.zhangqie.wanandroid.callback.ICallBack
import com.zhangqie.wanandroid.model.BaseModel
import com.zhangqie.wanandroid.ui.modular.callback.IModelCallback
import io.reactivex.disposables.Disposable

/**
 * Created by zhangqie on 2019/2/21
 * Describe:
 */
class NavigationModel(iCallBack: ICallBack<Any>) : BaseModel<ICallBack<Any>>() , IModelCallback {

    init {
        attachModel(iCallBack)
    }

    override fun showRequestModular() {
        addSubscription(jsonApiService.navigationDataInfo,object : SubscriberCallBack<NavigationInfo>(){

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }

            override fun onSuccess(result: NavigationInfo?) {
                Log.i("onSuccess", result!!.errorMsg)
                listener.onSuccess(result)
            }

            override fun onFailure(errorMsg: String?) {
                listener.onFailure(errorMsg)
            }

        })
    }


}