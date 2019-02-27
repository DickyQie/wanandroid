package com.zhangqie.wanandroid.ui.user


import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.zhangqie.wanandroid.R
import com.zhangqie.wanandroid.base.BaseActivity
import com.zhangqie.wanandroid.widget.RoundedCornersTransformation
import kotlinx.android.synthetic.main.my_content_activity.*
import kotlinx.android.synthetic.main.public_top.*


/**
 * Created by zhangqie on 2018/12/18.
 *
 * 个人中心
 *
 */

class MyContentActivity : BaseActivity() {


    override fun setMainLayout(): Int {
        return R.layout.my_content_activity
    }


    override fun initView() {
       home_tour_close.run {
           setOnClickListener {
               finish()
           }
       }

       home_top_name.run {
           text = resources.getText(R.string.nav_my_content)
       }

    }

    override fun initBeforeData() {
        val resourceId = R.mipmap.qie
        Glide.with(this)
                .load(resourceId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .bitmapTransform(RoundedCornersTransformation(this, 25))
                .into(my_logo)
    }

}
