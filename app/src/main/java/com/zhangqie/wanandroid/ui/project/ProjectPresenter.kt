package com.zhangqie.wanandroid.ui.project

import com.zhangqie.wanandroid.callback.ICallBack
import com.zhangqie.wanandroid.callback.IView
import com.zhangqie.wanandroid.model.BaseModel
import com.zhangqie.wanandroid.presenter.BasePresenter

/**
 * Created by zhangqie on 2019/2/21
 * Describe:
 */
class ProjectPresenter : BasePresenter<IView<Any>>(),ICallBack<Any> {

    var projectModel: ProjectModel? = null
    var iView: IView<Any>? = null

    fun showProjectTabList(){
        projectModel = ProjectModel(this)
        iView = getView()
        iView!!.onLoadContributorStart()
        projectModel!!.showRequestModular()
    }

    fun showProjectMenuListData(page: Int,cid: Int){
        projectModel = ProjectModel(this)
        iView = getView()
        iView!!.onLoadContributorStart()
        projectModel!!.showRequestProjectDataList(page, cid)
    }

    override fun onSuccess(r: Any?) {
        iView!!.onLoadContribtorComplete(r)
    }


    override fun onFailure(errorMsg: String?) {
        iView!!.onError(errorMsg)
    }

    override fun detachView() {
        super.detachView()
        if (projectModel!= null) {
            projectModel!!.detachModel()
        }
    }
}