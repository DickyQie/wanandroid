package com.zhangqie.wanandroid.ui.home

import android.content.Context
import com.zhangqie.wanandroid.callback.ICallBack
import com.zhangqie.wanandroid.callback.IView
import com.zhangqie.wanandroid.presenter.BasePresenter
import com.zhangqie.wanandroid.tool.NetUtils

/**
 * Created by zhangqie on 2019/2/18
 * Describe:
 */
class HomePresenter : BasePresenter<IView<Any>>(), ICallBack<Any> {

    var homeModel: HomeModel? = null
    var iView: IView<Any>? = null

    fun getBannerHttp(context: Context) {
        iView = getView()
        homeModel = HomeModel(this)
        iView!!.onLoadContributorStart()
        if (NetUtils.isNetworkAvailable(context)) {
            homeModel!!.showRequestBanner()
        }else{
            iView!!.onNetWork()
        }
    }

    fun getHomeDataHttp(page: String) {
        homeModel!!.showRequestListData(page)
    }


    override fun onSuccess(r: Any?) {
        if (iView != null) {
            iView!!.onLoadContribtorComplete(r)
        }
    }

    override fun onFailure(errorMsg: String?) {
        iView!!.onError(errorMsg)
    }

    override fun detachView() {
        super.detachView()
        homeModel!!.detachModel()
    }


}