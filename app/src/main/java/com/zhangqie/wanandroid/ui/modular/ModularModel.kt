package com.zhangqie.wanandroid.ui.modular

import android.util.Log
import com.zhangqie.wanandroid.api.SubscriberCallBack
import com.zhangqie.wanandroid.bean.ModularInfo
import com.zhangqie.wanandroid.callback.ICallBack
import com.zhangqie.wanandroid.model.BaseModel
import com.zhangqie.wanandroid.ui.modular.callback.IModelCallback
import io.reactivex.disposables.Disposable

/**
 * Created by zhangqie on 2019/2/20
 * Describe:
 */
class ModularModel(dataCallback: ICallBack<Any>) : BaseModel<ICallBack<Any>>(), IModelCallback {

    init {
        attachModel(dataCallback)
    }

    override fun showRequestModular() {
        addSubscription(jsonApiService.getModularDataInfo(), object : SubscriberCallBack<ModularInfo>(){

            override fun onComplete() {
                super.onComplete()
                Log.i("zhhstatus","onComplete")
            }
            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }

            override fun onSuccess(result: ModularInfo?) {
                Log.i("zhhstatus", result!!.errorMsg)
                listener.onSuccess(result)
            }

            override fun onFailure(errorMsg: String?) {
                Log.i("zhhstatus", "onFailure")
                listener.onFailure(errorMsg)
            }
        })
    }


}