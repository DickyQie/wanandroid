package com.zhangqie.wanandroid.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gyf.barlibrary.ImmersionBar
import com.zhangqie.wanandroid.R

import com.zhangqie.wanandroid.presenter.BasePresenter


abstract class BaseMvpFragment<V, T : BasePresenter<V>> : Fragment() {

    internal var p : T?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater!!.inflate(setMainLayout(),container,false)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        p = createPresenter()
        p!!.attachView(this as V)
        initView()
        initBeforeData()
    }


    abstract fun createPresenter(): T

    /***
     * 初始化布局
     */
    protected abstract fun setMainLayout(): Int

    /**
     * 初始化View
     */
    protected abstract fun initView()

    /**
     * 初始化先前数据
     */
    protected abstract fun initBeforeData()

    /***
     * 跳转Activity
     * @param mClass
     */
    protected fun openActivity(mClass: Class<*>) {
        openIntent(Intent(activity, mClass))
    }

    protected fun openIntent(intent: Intent) {
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        p!!.detachView()
    }


    protected fun showToast(message: String){
        Toast.makeText(activity,message, Toast.LENGTH_LONG).show()
    }

    protected fun showToast(message: Int){
        Toast.makeText(activity,message, Toast.LENGTH_LONG).show()
    }


    /**
     * 初始化沉浸式
     */
    open fun initImmersionBar() {
        ImmersionBar.with(this).keyboardEnable(true).init()
    }

}
