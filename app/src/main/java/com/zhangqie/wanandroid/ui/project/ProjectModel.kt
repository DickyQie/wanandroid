package com.zhangqie.wanandroid.ui.project

import com.zhangqie.wanandroid.api.SubscriberCallBack
import com.zhangqie.wanandroid.bean.ProjectListInfo
import com.zhangqie.wanandroid.bean.ProjectTreeBean
import com.zhangqie.wanandroid.callback.ICallBack
import com.zhangqie.wanandroid.model.BaseModel
import com.zhangqie.wanandroid.ui.modular.callback.IModelCallback
import io.reactivex.disposables.Disposable

/**
 * Created by zhangqie on 2019/2/21
 * Describe:
 */
class ProjectModel(iCallBack: ICallBack<Any>) : BaseModel<ICallBack<Any>>(), IModelCallback {

    init {
        attachModel(iCallBack)
    }

    override fun showRequestModular() {
        addSubscription(jsonApiService.projectTreeData,object : SubscriberCallBack<ProjectTreeBean>(){

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }

            override fun onSuccess(result: ProjectTreeBean?) {
                listener.onSuccess(result)
            }

            override fun onFailure(errorMsg: String?) {
                listener.onFailure(errorMsg)
            }

        })
    }

    fun showRequestProjectDataList(page: Int,cid: Int){
        addSubscription(jsonApiService.getProjectListData(page,cid),object : SubscriberCallBack<ProjectListInfo>(){

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }

            override fun onSuccess(result: ProjectListInfo?) {
                listener.onSuccess(result)
            }

            override fun onFailure(errorMsg: String?) {
                listener.onFailure(errorMsg)
            }

        })
    }

}