package com.zhangqie.wanandroid.ui.fragment

import android.content.Intent
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.cxz.wanandroid.utils.CommonUtil
import com.zhangqie.wanandroid.R
import com.zhangqie.wanandroid.base.BaseMvpFragment
import com.zhangqie.wanandroid.bean.ModularInfo
import com.zhangqie.wanandroid.callback.IView
import com.zhangqie.wanandroid.config.Constants
import com.zhangqie.wanandroid.loading.ProgressCancelListener
import com.zhangqie.wanandroid.loading.ProgressDialogHandler
import com.zhangqie.wanandroid.tool.ProgressDialogUtil
import com.zhangqie.wanandroid.ui.modular.ModularPresenter
import com.zhangqie.wanandroid.ui.modular.adapter.ModularAdapter
import com.zhangqie.wanandroid.ui.modular.ui.ModularActivity
import com.zhangqie.wanandroid.widget.RecyclerViewDivider
import com.zhangqie.zqrefresh.layout.SmartRefreshLayout
import com.zhangqie.zqrefresh.layout.constant.SpinnerStyle
import com.zhangqie.zqrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.modular_fragment.*

/**
 * Created by zhangqie on 2019/2/18
 * Describe: 知识模块
 */
class ModularFragment : BaseMvpFragment<IView<Any>,ModularPresenter>() ,IView<Any>,ProgressCancelListener{

    private var mProgressDialogHandler: ProgressDialogHandler? = null
    private var handler: Handler? = null

    private val layoutManager : LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    /**
     * RecyclerView Divider
     */
    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            RecyclerViewDivider(it,LinearLayoutManager.VERTICAL)
        }
    }

    /**
     * datas
     */
    private var modularList = mutableListOf<ModularInfo.DataBean>()


    /***
     * adapter
     */
    private val modularAdapter: ModularAdapter by lazy{
        ModularAdapter(activity,modularList)
    }

    override fun setMainLayout(): Int {
        return R.layout.modular_fragment
    }

    override fun createPresenter(): ModularPresenter {
        return ModularPresenter()
    }

    override fun initView() {
        val linearParams = toolbar_modular.getLayoutParams() as LinearLayout.LayoutParams
        linearParams.height = CommonUtil.getStatusBarHeight(activity)
        toolbar_modular.setLayoutParams(linearParams)

        modular_recycler.layoutManager = layoutManager
        handler = Handler()
        mProgressDialogHandler = ProgressDialogHandler(activity,this,true)

        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            val header = ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Scale)
            header.setPrimaryColorId(R.color.colorPrimaryDark)
            header.setAccentColorId(android.R.color.white)
            header
        }

        modular_recycler.addItemDecoration(recyclerViewItemDecoration)
    }


    override fun initBeforeData() {
        p!!.getModylarDataList()
    }

    override fun onLoadContributorStart() {
        ProgressDialogUtil.showProgressDialog(mProgressDialogHandler)
    }

    override fun onLoadContribtorComplete(topContributor: Any?) {
        if (topContributor is ModularInfo)
        {
            var modularInfo: ModularInfo = topContributor as ModularInfo
            if (modularInfo.data!!.size > 0 ){
                modularList.addAll(modularInfo.data!!)
                modular_recycler.adapter = modularAdapter
                modularAdapter.onItemClickListener = object : ModularAdapter.OnItemClickListener{
                    override fun OnItemClick(position: Int) {
                        Intent(activity, ModularActivity::class.java).run {
                            putExtra(Constants.CONTENT_TITLE_KEY, modularList[position].name)
                            putExtra(Constants.CONTENT_DATA_KEY, modularList[position])
                            startActivity(this)
                        }
                    }

                }
            }
            handler!!.postDelayed(Runnable { ProgressDialogUtil.dismissProgressDialog(mProgressDialogHandler) },1000)
        }

    }
    override fun onError(error: String?) {
        showToast(error!!)
    }

    override fun onNetWork() {
        showToast(R.string.no_network_tip)
    }

    override fun onCancelProgress() {
        mProgressDialogHandler = null
    }





}