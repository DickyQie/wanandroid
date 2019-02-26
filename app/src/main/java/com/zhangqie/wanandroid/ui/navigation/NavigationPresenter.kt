package com.zhangqie.wanandroid.ui.navigation

import com.zhangqie.wanandroid.callback.ICallBack
import com.zhangqie.wanandroid.callback.IView
import com.zhangqie.wanandroid.presenter.BasePresenter

/**
 * Created by zhangqie on 2019/2/21
 * Describe:
 */
class NavigationPresenter : BasePresenter<IView<Any>>(),ICallBack<Any> {

    var navigationModel: NavigationModel ?= null

    var iView:IView<Any>? = null

    fun showRequestNavigation(){
        navigationModel = NavigationModel(this)
        iView = getView()
        iView!!.onLoadContributorStart()
        navigationModel!!.showRequestModular()
    }

    override fun onSuccess(r: Any?) {
        iView!!.onLoadContribtorComplete(r)
    }

    override fun onFailure(errorMsg: String?) {
        iView!!.onError(errorMsg)
    }

    override fun detachView() {
        super.detachView()
        if (navigationModel!= null){
            navigationModel!!.onUnsubscribe()
        }
    }

}