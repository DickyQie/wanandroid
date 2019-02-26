package com.zhangqie.wanandroid.ui.user

import com.zhangqie.wanandroid.R
import com.zhangqie.wanandroid.base.BaseActivity
import com.zhangqie.wanandroid.tool.UtilFileDB
import kotlinx.android.synthetic.main.about_activity.*
import kotlinx.android.synthetic.main.public_top.*

/**
 * Created by zhangqie on 2019/2/22
 * Describe: 关于我们
 */
class AboutActivity : BaseActivity() {


    override fun setMainLayout(): Int {
        return R.layout.about_activity
    }

    override fun initView() {
        app_version.text = UtilFileDB.getVersion(this)
        home_top_name.setText(R.string.about_settings)
        home_tour_close.run {
            setOnClickListener { finish() }
        }
    }

    override fun initBeforeData() {

    }
}
