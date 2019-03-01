package com.zhangqie.wanandroid.ui.fragment

import android.content.Intent
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.zhangqie.wanandroid.R
import com.zhangqie.wanandroid.base.BaseMvpFragment
import com.zhangqie.wanandroid.bean.BannerInfo
import com.zhangqie.wanandroid.bean.HomeInfo
import com.zhangqie.wanandroid.callback.IView
import com.zhangqie.wanandroid.callback.OnItemClickListenter
import com.zhangqie.wanandroid.loading.ProgressCancelListener
import com.zhangqie.wanandroid.loading.ProgressDialogHandler
import com.zhangqie.wanandroid.tool.ProgressDialogUtil
import com.zhangqie.wanandroid.ui.adapter.HomeAdapter
import com.zhangqie.wanandroid.ui.home.HomePresenter
import com.zhangqie.wanandroid.ui.web.WebContentActivity
import com.zhangqie.wanandroid.widget.RecyclerViewDivider
import com.zhangqie.zqrefresh.layout.SmartRefreshLayout
import com.zhangqie.zqrefresh.layout.constant.SpinnerStyle
import com.zhangqie.zqrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.home_fragment.*

/**
 * Created by zhangqie on 2019/2/18
 * Describe:
 */
class HomeFragment : BaseMvpFragment<IView<Any>, HomePresenter>(), IView<Any>, ProgressCancelListener {

    private var mProgressDialogHandler: ProgressDialogHandler? = null
    private var page = 0
    private var handler: Handler? = null

    /**
     * LinearLayoutManager
     */
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity!!)
    }

    /**
     * RecyclerView Divider
     */
    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            RecyclerViewDivider(activity,LinearLayoutManager.VERTICAL)
        }
    }


    /**
     * datas
     */
    private var datas: List<BannerInfo.DataBean> = ArrayList<BannerInfo.DataBean>()

    /**
     * datas
     */
    private var dataList = mutableListOf<HomeInfo.DataBean.DatasBean>()

    /**
     * Home Adapter
     */
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(activity!!, datas, dataList)
    }


    override fun setMainLayout(): Int {
        return R.layout.home_fragment
    }

    override fun initView() {
        ImmersionBar.with(this).statusBarColorTransformEnable(false)
                .navigationBarColor(R.color.colorPrimary)
                .init()
        mProgressDialogHandler = ProgressDialogHandler(activity, this, true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(recyclerViewItemDecoration)
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            val header = ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Scale)
            header.setPrimaryColorId(R.color.colorPrimaryDark)
            header.setAccentColorId(android.R.color.white)
            header
        }

        refreshLayout.setEnableHeaderTranslationContent(true)
    }

    override fun createPresenter(): HomePresenter {
        return HomePresenter()
    }

    override fun initBeforeData() {

        handler = Handler()
        //刷新数据
        refreshLayout.setOnRefreshListener { refreshLayout ->
            refreshLayout.layout.postDelayed({
                refreshLayout.finishRefresh()
                refreshLayout.setNoMoreData(false)
                page = -1
                dataList.clear()
                showLoadPageDataModel()
            }, 500)
        }

        //加载更多
        refreshLayout.setOnLoadMoreListener { refreshLayout ->
            refreshLayout.layout.postDelayed({
                if (homeAdapter.getItemCount() > 60) {
                    showToast("数据全部加载完毕")
                    refreshLayout.finishLoadMoreWithNoMoreData()//将不会再次触发加载更多事件
                } else {
                    showLoadPageDataModel()
                    refreshLayout.finishLoadMore()
                }
            }, 500)
        }

        p!!.getBannerHttp(activity)
    }

    /***
     * 分页加载数据
     */
    fun showLoadPageDataModel() {
        page++
        p!!.getHomeDataHttp(page.toString())
    }

    override fun onLoadContributorStart() {
        ProgressDialogUtil.showProgressDialog(mProgressDialogHandler)
    }

    override fun onLoadContribtorComplete(topContributor: Any?) {
     if (topContributor is BannerInfo) {
            val bannerInfo = topContributor as BannerInfo
            datas = bannerInfo.data!!
            showLoadPageDataModel()
        } else if (topContributor is HomeInfo) {
            val homeInfo = topContributor as HomeInfo
            if (dataList.size < 1) {
                dataList.addAll(homeInfo.data!!.datas)
                recyclerView.adapter = homeAdapter
                handler!!.postDelayed({   ProgressDialogUtil.dismissProgressDialog(mProgressDialogHandler) }, 1000)
            } else {
                dataList.addAll(homeInfo.data.datas)
                homeAdapter.notifyDataSetChanged()
            }
        }
        homeAdapter!!.setOnItemClickListenter(object : OnItemClickListenter {
            override fun onItemClick(view: View, position: Int) {
                var intent: Intent = Intent(activity, WebContentActivity::class.java)
                intent.putExtra("title",dataList[position-1].title)
                intent.putExtra("link",dataList[position-1].link)
                openIntent(intent)
            }
        })


    }


    override fun onNetWork() {
        handler!!.postDelayed({
            ProgressDialogUtil.dismissProgressDialog(mProgressDialogHandler)
            showToast(R.string.no_network_tip)
        }, 1000)

    }

    override fun onError(error: String?) {
        showToast(error!!)
    }

    override fun onCancelProgress() {
        //关闭进度条之后的操作
        mProgressDialogHandler = null
        handler= null
    }


}