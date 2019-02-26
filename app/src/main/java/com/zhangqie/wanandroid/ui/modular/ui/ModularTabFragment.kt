package com.zhangqie.wanandroid.ui.modular.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.zhangqie.wanandroid.R
import com.zhangqie.wanandroid.base.BaseMvpFragment
import com.zhangqie.wanandroid.bean.ModularListInfo
import com.zhangqie.wanandroid.callback.IView
import com.zhangqie.wanandroid.callback.OnItemClickListenter
import com.zhangqie.wanandroid.config.Constants
import com.zhangqie.wanandroid.loading.ProgressCancelListener
import com.zhangqie.wanandroid.loading.ProgressDialogHandler
import com.zhangqie.wanandroid.tool.ProgressDialogUtil
import com.zhangqie.wanandroid.ui.modular.adapter.ModularTabListAdapter
import com.zhangqie.wanandroid.ui.modular.ui.mvp.ModularTabPresenter
import com.zhangqie.wanandroid.ui.web.WebContentActivity
import com.zhangqie.wanandroid.widget.RecyclerViewDivider
import com.zhangqie.zqrefresh.layout.SmartRefreshLayout
import com.zhangqie.zqrefresh.layout.constant.SpinnerStyle
import com.zhangqie.zqrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.home_fragment.*

/**
 * Created by zhangqie on 2019/2/22
 * Describe:
 */

class ModularTabFragment : BaseMvpFragment<IView<Any>, ModularTabPresenter>(), IView<Any>,ProgressCancelListener {

    companion object {
        fun getInstance(id: Int): ModularTabFragment {
            val fragment = ModularTabFragment()
            val args = Bundle()
            args.putInt(Constants.CONTENT_CID_KEY, id)
            fragment.arguments = args
            return fragment
        }
    }

    /**
     * cid
     */
    private var cid: Int = -1

    private var page: Int = -1


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
            RecyclerViewDivider(it,LinearLayoutManager.VERTICAL)
        }
    }

    var progressDialogHandler: ProgressDialogHandler? = null
    var handler: Handler? = null

    /**
     * datas
     */
    private var dataList = mutableListOf<ModularListInfo.DataBean.DatasBean>()

    /**
     * Adapter
     */
    private val projectListAdapter: ModularTabListAdapter by lazy {
        ModularTabListAdapter(activity!!, dataList)
    }


    override fun createPresenter(): ModularTabPresenter {
        return ModularTabPresenter()
    }

    override fun setMainLayout(): Int {
        return R.layout.home_fragment
    }

    override fun initView() {
        cid = arguments!!.getInt(Constants.CONTENT_CID_KEY) ?: -1
        recyclerView.layoutManager = linearLayoutManager
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            val header = ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Scale)
            header.setPrimaryColorId(R.color.colorPrimaryDark)
            header.setAccentColorId(android.R.color.white)
            header
        }
        recyclerView.itemAnimator= DefaultItemAnimator()
        recyclerView.addItemDecoration(recyclerViewItemDecoration)
        handler = Handler()
        progressDialogHandler = ProgressDialogHandler(activity!!,this,true)
    }

    override fun initBeforeData() {
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
                if (projectListAdapter.getItemCount() > 60) {
                    showToast("数据全部加载完毕")
                    refreshLayout.finishLoadMoreWithNoMoreData()//将不会再次触发加载更多事件
                } else {
                    showLoadPageDataModel()
                    refreshLayout.finishLoadMore()
                }
            }, 500)
        }
        showLoadPageDataModel()
    }

    /***
     * 分页加载数据
     */
    fun showLoadPageDataModel() {
        page++
        p!!.showModularTabList(page, cid)
    }

    override fun onLoadContributorStart() {
        ProgressDialogUtil.showProgressDialog(progressDialogHandler)
    }

    override fun onLoadContribtorComplete(topContributor: Any?) {
        if (topContributor is ModularListInfo) {
            val projectListInfo = topContributor as ModularListInfo
            if (dataList.size < 1) {
                dataList.addAll(projectListInfo.data!!.datas!!)
                recyclerView.adapter = projectListAdapter
            } else {
                dataList.addAll(projectListInfo.data!!.datas!!)
                projectListAdapter.notifyDataSetChanged()
            }
            handler!!.postDelayed({   ProgressDialogUtil.dismissProgressDialog(progressDialogHandler) }, 1000)
        }

        projectListAdapter!!.setOnItemClickListenter(object : OnItemClickListenter {
            override fun onItemClick(view: View, position: Int) {
                var intent: Intent = Intent(activity, WebContentActivity::class.java)
                intent.putExtra("title", dataList[position].title)
                intent.putExtra("link", dataList[position].link)
                openIntent(intent)
            }
        })



    }

    override fun onError(error: String?) {
        showToast(error!!)
    }

    override fun onNetWork() {
        showToast(R.string.no_network_tip)
    }

    override fun onCancelProgress() {
        handler = null

    }
}