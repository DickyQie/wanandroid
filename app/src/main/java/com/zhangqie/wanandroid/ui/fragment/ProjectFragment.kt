package com.zhangqie.wanandroid.ui.fragment

import android.os.Handler
import android.support.design.widget.TabLayout
import android.widget.LinearLayout
import com.cxz.wanandroid.utils.CommonUtil
import com.zhangqie.wanandroid.R
import com.zhangqie.wanandroid.base.BaseMvpFragment
import com.zhangqie.wanandroid.bean.ProjectTreeBean
import com.zhangqie.wanandroid.callback.IView
import com.zhangqie.wanandroid.loading.ProgressCancelListener
import com.zhangqie.wanandroid.loading.ProgressDialogHandler
import com.zhangqie.wanandroid.tool.ProgressDialogUtil
import com.zhangqie.wanandroid.ui.project.ProjectPresenter
import com.zhangqie.wanandroid.ui.project.adapter.ProjectPagerAdapter
import com.zhangqie.wanandroid.widget.TabLayoutHelper
import kotlinx.android.synthetic.main.project_fragment.*

/**
 * Created by zhangqie on 2019/2/18
 * Describe:
 */
class ProjectFragment : BaseMvpFragment<IView<Any>,ProjectPresenter>(),IView<Any>,ProgressCancelListener {

    var progressDialogHandler: ProgressDialogHandler? = null
    var handler: Handler? = null

    val projectList = mutableListOf<ProjectTreeBean.DataBean>()


    /**
     * ViewPagerAdapter
     */
    private val viewPagerAdapter: ProjectPagerAdapter by lazy {
        ProjectPagerAdapter(projectList, fragmentManager)
    }

    override fun createPresenter(): ProjectPresenter {
        return ProjectPresenter()
    }

    override fun setMainLayout(): Int {
        return R.layout.project_fragment
    }

    override fun initView() {
        val linearParams = project_toolbar.getLayoutParams() as LinearLayout.LayoutParams
        linearParams.height = CommonUtil.getStatusBarHeight(activity)
        project_toolbar.setLayoutParams(linearParams)


        handler = Handler()
        progressDialogHandler = ProgressDialogHandler(activity!!,this,true)

        viewPager.run {
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        }

        tabLayout.run {
            setupWithViewPager(viewPager)
            TabLayoutHelper.setUpIndicatorWidth(tabLayout)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
        }

    }



    override fun initBeforeData() {
        p!!.showProjectTabList()
    }

    override fun onLoadContributorStart() {
        ProgressDialogUtil.showProgressDialog(progressDialogHandler)
    }

    override fun onLoadContribtorComplete(topContributor: Any?) {
        var projectTreeBean:ProjectTreeBean = topContributor as ProjectTreeBean
        if (projectTreeBean.data!!.size > 0){
            projectList.addAll(projectTreeBean.data!!)
            viewPager.run {
                adapter = viewPagerAdapter
                offscreenPageLimit = projectTreeBean.data!!.size
                }

        }

        handler!!.postDelayed(Runnable {
            ProgressDialogUtil.dismissProgressDialog(progressDialogHandler)
        },1000)


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






}