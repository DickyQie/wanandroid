package com.zhangqie.wanandroid.ui.user.register

import com.zhangqie.wanandroid.api.SubscriberCallBack
import com.zhangqie.wanandroid.bean.LoginData
import com.zhangqie.wanandroid.callback.ICallBack
import com.zhangqie.wanandroid.model.BaseModel
import io.reactivex.disposables.Disposable

/**
 * Created by zhangqie on 2019/2/20
 * Describe:
 */
class RegisterModel(dataCallback: ICallBack<Any>) : BaseModel<ICallBack<Any>>() {

    init {
        attachModel(dataCallback)
    }

    fun showRequestRegister(userName: String,password: String) {
        addSubscription(jsonApiService.registerWanAndroid(userName,password,password), object : SubscriberCallBack<LoginData>(){

            override fun onComplete() {
                super.onComplete()
            }
            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }

            override fun onSuccess(result: LoginData?) {
                listener.onSuccess(result)
            }

            override fun onFailure(errorMsg: String?) {
                listener.onFailure(errorMsg)
            }
        })
    }


}