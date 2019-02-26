package com.zhangqie.wanandroid.ui.project.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.zhangqie.wanandroid.bean.ProjectTreeBean
import com.zhangqie.wanandroid.ui.project.view.ProjectListFragment

/**
 * Created by chenxz on 2018/5/20.
 */
class ProjectPagerAdapter(private val list: MutableList<ProjectTreeBean.DataBean>, fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    private val fragments = mutableListOf<Fragment>()

    init {
        fragments.clear()
        list.forEach {
            fragments.add(ProjectListFragment.getInstance(it.id))
        }
    }

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence? = list[position].name

    override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE
}