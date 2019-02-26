package com.zhangqie.wanandroid.ui.modular.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.zhangqie.wanandroid.bean.ModularInfo
import com.zhangqie.wanandroid.ui.modular.ui.ModularTabFragment

class ModularPagerAdapter(val list: List<ModularInfo.DataBean.ChildrenBean>, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragments = mutableListOf<Fragment>()

    init {
        list.forEach {
            fragments.add(ModularTabFragment.getInstance(it.id))
        }
    }

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence? = list[position].name

    override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE
}