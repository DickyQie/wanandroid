package com.zhangqie.wanandroid.presenter

interface IPresenter<V> {

    fun attachView(view: V)

    fun detachView()

}
