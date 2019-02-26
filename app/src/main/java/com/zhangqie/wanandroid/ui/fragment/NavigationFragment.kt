package com.zhangqie.wanandroid.ui.fragment

import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.cxz.wanandroid.utils.CommonUtil
import com.zhangqie.wanandroid.R
import com.zhangqie.wanandroid.base.BaseMvpFragment
import com.zhangqie.wanandroid.bean.NavigationInfo
import com.zhangqie.wanandroid.callback.IView
import com.zhangqie.wanandroid.loading.ProgressCancelListener
import com.zhangqie.wanandroid.loading.ProgressDialogHandler
import com.zhangqie.wanandroid.tool.ProgressDialogUtil
import com.zhangqie.wanandroid.ui.navigation.NavigationPresenter
import com.zhangqie.wanandroid.ui.navigation.adapter.NavigationAdapter
import com.zhangqie.wanandroid.ui.navigation.adapter.NavigationTabAdapter
import kotlinx.android.synthetic.main.navigation_fragment.*
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.widget.TabView

/**
 * Created by zhangqie on 2019/2/18
 * Describe:
 */
class NavigationFragment : BaseMvpFragment<IView<Any>,NavigationPresenter>() ,IView<Any>,ProgressCancelListener{


    private var bScroll: Boolean = false
    private var currentIndex: Int = 0
    private var bClickTab: Boolean = false
    var progressDialogHandler: ProgressDialogHandler? = null
    var handler: Handler? = null

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    override fun createPresenter(): NavigationPresenter {
        return NavigationPresenter()
    }

    private var navigationList = mutableListOf<NavigationInfo.DataBean>()

    private val navigationAdapter: NavigationAdapter by lazy {
        NavigationAdapter(activity,navigationList)
    }


    override fun setMainLayout(): Int {
        return R.layout.navigation_fragment
    }

    override fun initView() {

        val linearParams = toolbar.getLayoutParams() as LinearLayout.LayoutParams
        linearParams.height = CommonUtil.getStatusBarHeight(activity)
        toolbar.setLayoutParams(linearParams)


        progressDialogHandler = ProgressDialogHandler(activity,this,true)
        p!!.showRequestNavigation()
        navigation_recycler.layoutManager = linearLayoutManager
        handler = Handler()
    }

    override fun initBeforeData() {

    }

    override fun onLoadContributorStart() {
        ProgressDialogUtil.showProgressDialog(progressDialogHandler)
    }

    override fun onLoadContribtorComplete(topContributor: Any?) {
        if (topContributor is NavigationInfo){
            var navigationInfo : NavigationInfo = topContributor as NavigationInfo
            if (navigationInfo.data.size>0){
                navigationList.addAll(navigationInfo.data)
                navigation_tab_layout.setTabAdapter(NavigationTabAdapter(activity,navigationInfo.data))
                navigation_recycler.adapter = navigationAdapter
            }
            handler!!.postDelayed(Runnable {
                ProgressDialogUtil.dismissProgressDialog(progressDialogHandler)
            },1000)

            navigation_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (bScroll && (newState == RecyclerView.SCROLL_STATE_IDLE)) {
                        scrollRecyclerView()
                    }
                    rightLinkLeft(newState)
                }

                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (bScroll) {
                        scrollRecyclerView()
                    }
                }
            })
            navigation_tab_layout.addOnTabSelectedListener(object : VerticalTabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabView?, position: Int) {
                }

                override fun onTabSelected(tab: TabView?, position: Int) {
                    bClickTab = true
                    selectTab(position)
                }
            })

        }
    }

    override fun onError(error: String?) {
        showToast(error!!)
    }

    override fun onNetWork() {
        showToast(R.string.no_network_tip)
    }

    override fun onCancelProgress() {
        progressDialogHandler = null
        handler = null
    }


    private fun scrollRecyclerView() {
        bScroll = false
        val indexDistance: Int = currentIndex - linearLayoutManager.findFirstVisibleItemPosition()
        if (indexDistance > 0 && indexDistance < navigation_recycler!!.childCount) {
            val top: Int = navigation_recycler.getChildAt(indexDistance).top
            navigation_recycler.smoothScrollBy(0, top)
        }
    }

    /**
     * Right RecyclerView link Left TabLayout
     *
     * @param newState RecyclerView Scroll State
     */
    private fun rightLinkLeft(newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (bClickTab) {
                bClickTab = false
                return
            }
            val firstPosition: Int = linearLayoutManager.findFirstVisibleItemPosition()
            if (firstPosition != currentIndex) {
                currentIndex = firstPosition
                setChecked(currentIndex)
            }
        }
    }


    /**
     * Smooth Right RecyclerView to Select Left TabLayout
     *
     * @param position checked position
     */
    private fun setChecked(position: Int) {
        if (bClickTab) {
            bClickTab = false
        } else {
            navigation_tab_layout.setTabSelected(currentIndex)
        }
        currentIndex = position
    }

    /**
     * Select Left TabLayout to Smooth Right RecyclerView
     */
    private fun selectTab(position: Int) {
        currentIndex = position
        navigation_recycler.stopScroll()
        smoothScrollToPosition(position)
    }

    private fun smoothScrollToPosition(position: Int) {
        val firstPosition: Int = linearLayoutManager.findFirstVisibleItemPosition()
        val lastPosition: Int = linearLayoutManager.findLastVisibleItemPosition()
        when {
            position <= firstPosition -> {
                navigation_recycler.smoothScrollToPosition(position)
            }
            position <= lastPosition -> {
                val top: Int = navigation_recycler.getChildAt(position - firstPosition).top
                navigation_recycler.smoothScrollBy(0, top)
            }
            else -> {
                navigation_recycler.smoothScrollToPosition(position)
                bScroll = true
            }
        }
    }

}