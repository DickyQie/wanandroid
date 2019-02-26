package com.zhangqie.wanandroid.ui.web

import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.LinearLayout
import com.just.library.AgentWeb
import com.zhangqie.wanandroid.R
import com.zhangqie.wanandroid.base.BaseActivity

/**
 * Created by zhangqie on 2019/2/19
 * Describe:
 */
class WebContentActivity : BaseActivity() {

    private var mAgentWeb: AgentWeb? = null
    private var toolbar: Toolbar? = null
    private var url: String? = null

    override fun setMainLayout(): Int {
        return R.layout.webview_content
    }

    override fun initView() {
        url = intent.getStringExtra("link")
        toolbar = findViewById(R.id.toolbar)
        toolbar!!.setTitle(intent.getStringExtra("title"))
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun initBeforeData() {
        val mLinearLayout = findViewById<LinearLayout>(R.id.layout_wenview)
        mAgentWeb = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(mLinearLayout, LinearLayout.LayoutParams(-1, -1))
                //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                .useDefaultIndicator()// 使用默认进度条
                .defaultProgressBarColor() // 使用默认进度条颜色
                // .setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()//
                .ready()
                .go(url)//"http://www.jd.com"
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (mAgentWeb!!.handleKeyEvent(keyCode, event)) {
            return true
        } else {
            finish()
            return super.onKeyDown(keyCode, event)
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}