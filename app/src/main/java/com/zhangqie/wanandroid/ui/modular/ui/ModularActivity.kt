package com.zhangqie.wanandroid.ui.modular.ui

import android.support.design.widget.TabLayout
import android.view.View
import com.zhangqie.wanandroid.R
import com.zhangqie.wanandroid.base.BaseActivity
import com.zhangqie.wanandroid.bean.ModularInfo
import com.zhangqie.wanandroid.config.Constants
import com.zhangqie.wanandroid.ui.modular.adapter.ModularPagerAdapter
import kotlinx.android.synthetic.main.modular_activity.*

/**
 * Created by zhangqie on 2019/2/22
 * Describe:
 */
class ModularActivity : BaseActivity(){

    /**
     * datas
     */
    private var childrenBean = mutableListOf<ModularInfo.DataBean.ChildrenBean>()


    override fun setMainLayout(): Int {
        return R.layout.modular_activity
    }


    /**
     * ViewPagerAdapter
     */
    private val viewPagerAdapter: ModularPagerAdapter by lazy {
        ModularPagerAdapter(childrenBean, supportFragmentManager)
    }


    override fun initView() {
        intent.extras.let {
            toolbar_item.title = it.getString(Constants.CONTENT_TITLE_KEY)
            it.getSerializable(Constants.CONTENT_DATA_KEY).let {
                val data = it as ModularInfo.DataBean
                childrenBean.addAll(data.children!!)
            }
        }
    }


    override fun initBeforeData() {
        toolbar_item.run {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        viewPager.run {
            adapter = viewPagerAdapter
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
            offscreenPageLimit = childrenBean.size
        }
        tabLayout.run {
            setupWithViewPager(viewPager)
            // TabLayoutHelper.setUpIndicatorWidth(tabLayout)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        }

        toolbar_item.setNavigationOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                finish()
            }
        })


    }


}