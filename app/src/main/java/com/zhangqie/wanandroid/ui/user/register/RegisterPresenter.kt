package com.zhangqie.wanandroid.ui.user.register

import com.zhangqie.wanandroid.callback.ICallBack
import com.zhangqie.wanandroid.callback.IView
import com.zhangqie.wanandroid.presenter.BasePresenter

/**
 * Created by zhangqie on 2019/2/20
 * Describe:
 */
class RegisterPresenter : BasePresenter<IView<Any>>(),ICallBack<Any>{

    var registerModel: RegisterModel? = null
    var iView: IView<Any>? = null

    fun getRegisterLnt(userName: String,password: String){
        iView = getView()
        registerModel = RegisterModel(this)
        iView!!.onLoadContributorStart()
        registerModel!!.showRequestRegister(userName, password)
    }

    override fun onSuccess(r: Any?) {
        iView!!.onLoadContribtorComplete(r)
    }

    override fun onFailure(errorMsg: String?) {
        iView!!.onError(errorMsg)
    }

    override fun detachView() {
        super.detachView()
        if (registerModel != null) {
            registerModel!!.detachModel()
        }
    }


}