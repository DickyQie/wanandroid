package com.zhangqie.wanandroid.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

import com.zhangqie.wanandroid.presenter.BasePresenter


abstract class BaseMvpActivity<V, T : BasePresenter<V>> : AppCompatActivity() {

    protected var p: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setMainLayout())
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
        openIntent(Intent(this, mClass))
    }

    protected fun openIntent(intent: Intent) {
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        p!!.detachView()
    }


    protected fun showToast(message: String){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }

    protected fun showToast(message: Int){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }


}
