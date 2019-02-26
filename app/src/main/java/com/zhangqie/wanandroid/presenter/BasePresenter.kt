package com.zhangqie.wanandroid.presenter


import java.lang.ref.WeakReference

abstract class BasePresenter<T> : IPresenter<T> {

    internal var weakReference: WeakReference<T>? = null

    override fun attachView(view: T) {
        weakReference = WeakReference(view)
    }

    override fun detachView() {
        if (weakReference != null) {
            weakReference!!.clear()
            weakReference = null
        }
    }

    fun getView(): T? {
        return weakReference!!.get()
    }

}
