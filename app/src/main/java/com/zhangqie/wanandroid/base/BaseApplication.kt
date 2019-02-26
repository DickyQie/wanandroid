package com.zhangqie.wanandroid.base

import android.app.Application
import android.content.Context
import android.content.SharedPreferences


/**
 * Created by zhangqie on 2018/6/5 0005
 *
 * app启动配置类
 *
 */
open class BaseApplication : Application() {

    var sharedName: String? = null

    override fun onCreate() {
        super.onCreate()
        preferences = getSharedPreferences(sharedName, Context.MODE_PRIVATE)
        editor = preferences!!.edit()
    }

    companion object {
        /**
         * 小型数据库读取
         */
        var preferences: SharedPreferences ?= null
        /**
         * 小型数据库写入
         */
        var editor: SharedPreferences.Editor ?= null
    }


}
