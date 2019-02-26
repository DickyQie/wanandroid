package com.zhangqie.wanandroid.ui.modular.ui.mvp

import com.zhangqie.wanandroid.callback.ICallBack
import com.zhangqie.wanandroid.callback.IView
import com.zhangqie.wanandroid.presenter.BasePresenter

/**
 * Created by zhangqie on 2019/2/21
 * Describe:
 */
class ModularTabPresenter : BasePresenter<IView<Any>>(),ICallBack<Any> {

    var modularTabModel: ModularTabModel? = null
    var iView: IView<Any>? = null

    fun showModularTabList(page: Int,id: Int){
        modularTabModel = ModularTabModel(this)
        iView = getView()
        iView!!.onLoadContributorStart()
        modularTabModel!!.showRequestProjectDataList(page,id)
    }

    override fun onSuccess(r: Any?) {
        iView!!.onLoadContribtorComplete(r)
    }

    override fun onFailure(errorMsg: String?) {
        iView!!.onError(errorMsg)
    }

    override fun detachView() {
        super.detachView()
        if (modularTabModel!= null) {
            modularTabModel!!.detachModel()
        }
    }
}