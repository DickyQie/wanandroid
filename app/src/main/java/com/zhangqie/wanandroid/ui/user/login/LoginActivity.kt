package com.zhangqie.wanandroid.ui.user.login

import android.os.Handler
import android.view.View
import com.zhangqie.wanandroid.R
import com.zhangqie.wanandroid.base.BaseMvpActivity
import com.zhangqie.wanandroid.bean.LoginData
import com.zhangqie.wanandroid.callback.IView
import com.zhangqie.wanandroid.loading.ProgressCancelListener
import com.zhangqie.wanandroid.loading.ProgressDialogHandler
import com.zhangqie.wanandroid.tool.ProgressDialogUtil
import com.zhangqie.wanandroid.tool.UtilFileDB
import com.zhangqie.wanandroid.ui.user.register.RegisterActivity
import kotlinx.android.synthetic.main.login_activity.*

/**
 * Created by zhangqie on 2019/2/22
 * Describe:登录
 */
class LoginActivity : BaseMvpActivity<IView<Any>, LoginPresenter>(), IView<Any>, ProgressCancelListener,View.OnClickListener{


    override fun setMainLayout(): Int {
        return R.layout.login_activity
    }

    override fun initView() {
        btn_login.setOnClickListener(this)
        tv_register_in.setOnClickListener(this)
        handler = Handler()
        mProgressDialogHandler = ProgressDialogHandler(this,this,true)

    }


    private var mProgressDialogHandler: ProgressDialogHandler? = null
    private var handler: Handler? = null

    override fun createPresenter(): LoginPresenter {
        return LoginPresenter()
    }


    override fun initBeforeData() {

    }


    override fun onLoadContributorStart() {
        ProgressDialogUtil.showProgressDialog(mProgressDialogHandler)
    }

    override fun onLoadContribtorComplete(topContributor: Any?) {
        if (topContributor is LoginData)
        {
            var loginData: LoginData = topContributor as LoginData
            if (loginData.errorCode == 0){
                showToast(getString(R.string.login_success))
                UtilFileDB.ADDSHAREDDATA("wanusername",loginData.data!!.username!!)
                handler!!.postDelayed(Runnable {
                    ProgressDialogUtil.dismissProgressDialog(mProgressDialogHandler)
                    setResult(2)
                    finish()
                },1000)
            }else{
                showToast(loginData.errorMsg!!)
                handler!!.postDelayed(Runnable {
                    ProgressDialogUtil.dismissProgressDialog(mProgressDialogHandler)
                },1000)
            }

        }

    }
    override fun onError(error: String?) {
        showToast(error!!)
    }

    override fun onNetWork() {
        showToast(R.string.no_network_tip)
    }

    override fun onCancelProgress() {
        handler = null
        mProgressDialogHandler = null
    }

    override fun onClick(v: View?) {
        if (v!!.id == R.id.btn_login){
            login()
        }else if (v!!.id == R.id.tv_register_in){
            openActivity(RegisterActivity::class.java)
        }
    }


    /**
     * Login
     */
    private fun login() {
        if (validate()) {
            p!!.loginWanAndroid(et_username.text.toString(), et_password.text.toString())
        }

    }

    /**
     * Check UserName and PassWord
     */
    private fun validate(): Boolean {
        var valid = true
        val username: String = et_username.text.toString()
        val password: String = et_password.text.toString()
        if (username.isEmpty()) {
            et_username.error = getString(R.string.username_not_empty)
            valid = false
        }
        if (password.isEmpty()) {
            et_password.error = getString(R.string.password_not_empty)
            valid = false
        }
        return valid

    }




}
