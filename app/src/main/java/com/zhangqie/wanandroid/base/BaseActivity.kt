package com.zhangqie.wanandroid.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast


abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setMainLayout())
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
        openIntent(Intent(this, mClass))
    }

    protected fun openIntent(intent: Intent) {
        startActivity(intent)
    }


    protected fun openForResyltActivity(intent: Intent, requestCode: Int) {
        startActivityForResult(intent, requestCode)
    }

    protected fun showToast(message: String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

}
