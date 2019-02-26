package com.zhangqie.wanandroid.ui.modular

import com.zhangqie.wanandroid.callback.ICallBack
import com.zhangqie.wanandroid.callback.IView
import com.zhangqie.wanandroid.presenter.BasePresenter

/**
 * Created by zhangqie on 2019/2/20
 * Describe:
 */
class ModularPresenter : BasePresenter<IView<Any>>(),ICallBack<Any>{

    var modularModel: ModularModel? = null
    var iView: IView<Any>? = null

    fun getModylarDataList(){
        iView = getView()
        modularModel = ModularModel(this)
        iView!!.onLoadContributorStart()
        modularModel!!.showRequestModular()
    }

    override fun onSuccess(r: Any?) {
        iView!!.onLoadContribtorComplete(r)
    }

    override fun onFailure(errorMsg: String?) {
        iView!!.onError(errorMsg)
    }

    override fun detachView() {
        super.detachView()
        if (modularModel != null) {
            modularModel!!.detachModel()
        }
    }


}