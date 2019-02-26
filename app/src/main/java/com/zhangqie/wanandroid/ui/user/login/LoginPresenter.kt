package com.zhangqie.wanandroid.ui.user.login

import com.zhangqie.wanandroid.callback.ICallBack
import com.zhangqie.wanandroid.callback.IView
import com.zhangqie.wanandroid.presenter.BasePresenter

/**
 * Created by zhangqie on 2019/2/20
 * Describe:
 */
class LoginPresenter : BasePresenter<IView<Any>>(),ICallBack<Any>{

    var loginModel: LoginModel? = null
    var iView: IView<Any>? = null

    fun loginWanAndroid(userName: String,password: String){
        iView = getView()
        loginModel = LoginModel(this)
        iView!!.onLoadContributorStart()
        loginModel!!.showRequestRegister(userName, password)
    }

    override fun onSuccess(r: Any?) {
        iView!!.onLoadContribtorComplete(r)
    }

    override fun onFailure(errorMsg: String?) {
        iView!!.onError(errorMsg)
    }

    override fun detachView() {
        super.detachView()
        if (loginModel != null) {
            loginModel!!.detachModel()
        }
    }


}