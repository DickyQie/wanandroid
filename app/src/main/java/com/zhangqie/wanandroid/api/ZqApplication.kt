package com.zhangqie.wanandroid.api

import android.support.v7.app.AppCompatDelegate
import com.zhangqie.wanandroid.R
import com.zhangqie.wanandroid.base.BaseApplication
import com.zhangqie.wanandroid.tool.DynamicTimeFormat
import com.zhangqie.zqrefresh.layout.SmartRefreshLayout
import com.zhangqie.zqrefresh.layout.header.ClassicsHeader

/**
 * Created by zhangqie on 2018/10/11
 * Describe:
 */
class ZqApplication : BaseApplication() {

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.White, android.R.color.background_dark)//全局设置主题颜色
            ClassicsHeader(context).setTimeFormat(DynamicTimeFormat("更新于 %s"))
        }
    }

    override fun onCreate() {
        super.onCreate()
        sharedName = "zhangqie"

    }
}