package com.zhangqie.wanandroid.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gyf.barlibrary.ImmersionBar


abstract class BaseFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater!!.inflate(setMainLayout(), container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initBeforeData()
    }


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


    protected fun openForResyltActivity(intent: Intent, requestCode: Int) {
        startActivityForResult(intent, requestCode)
    }

    protected fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    /**
     * 初始化沉浸式
     */
    protected fun initImmersionBar() {
        ImmersionBar.with(this).keyboardEnable(true).init()
    }

}
