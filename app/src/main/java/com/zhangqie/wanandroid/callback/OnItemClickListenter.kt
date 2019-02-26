package com.zhangqie.wanandroid.callback

import android.view.View


/**
 * Created by zhangqie on 2018/11/23
 * Describe: RecyclerView Item点击事件
 */

interface OnItemClickListenter {

    fun onItemClick(view: View, position: Int)

}
