package com.zhangqie.wanandroid.ui.home

import android.util.Log
import com.zhangqie.wanandroid.api.SubscriberCallBack
import com.zhangqie.wanandroid.bean.BannerInfo
import com.zhangqie.wanandroid.bean.HomeInfo
import com.zhangqie.wanandroid.callback.ICallBack
import com.zhangqie.wanandroid.model.BaseModel
import com.zhangqie.wanandroid.ui.home.view.IModelCallback
import io.reactivex.disposables.Disposable

/**
 * Created by zhangqie on 2019/2/18
 * Describe:
 */
class HomeModel(orderICallBack: ICallBack<Any>) : BaseModel<ICallBack<Any>>(), IModelCallback {

    init {
        attachModel(orderICallBack)
    }

    override fun showRequestBanner() {
        addSubscription(jsonApiService.getBannerInfo(), object : SubscriberCallBack<BannerInfo>() {

            override fun onComplete() {

            }
            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }

            override fun onSuccess(bannerInfo: BannerInfo) {
                Log.i("aaa", bannerInfo.errorMsg)
                listener.onSuccess(bannerInfo)
            }

            override fun onFailure(errorMsg: String) {
                listener.onFailure(errorMsg)
            }

        })
    }

    override fun showRequestListData(page: String) {
        addSubscription(jsonApiService.getHomeDataInfo(page), object : SubscriberCallBack<HomeInfo>() {

            override fun onComplete() {

            }
            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }

            override fun onSuccess(homeInfo: HomeInfo) {
                Log.i("zhhstatus", homeInfo.errorMsg)
                listener.onSuccess(homeInfo)
            }

            override fun onFailure(errorMsg: String) {
                listener.onFailure(errorMsg)
            }

        })

    }
/*
     fun showRequestMap(map: Map<String, Any>) {
        addSubscription(jsonApiService.getBorrowOrderByBorrowCode(map), object : SubscriberCallBack<BorrowRecordInfo>() {

            override fun onComplete() {

            }
            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }

            override fun onSuccess(borrowRecordInfo: BorrowRecordInfo) {
                Log.i("aaa", borrowRecordInfo.msg)
                listener.onSuccess(borrowRecordInfo.order)
            }

            override fun onFailure(errorMsg: String) {
                listener.onFailure(errorMsg)
            }

        })
    }
   */
}
