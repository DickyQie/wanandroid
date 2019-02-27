package com.zhangqie.wanandroid.ui.user.register

import android.content.Intent
import android.os.Handler
import android.view.View
import com.zhangqie.wanandroid.R
import com.zhangqie.wanandroid.base.BaseMvpActivity
import com.zhangqie.wanandroid.bean.LoginData
import com.zhangqie.wanandroid.callback.IView
import com.zhangqie.wanandroid.loading.ProgressCancelListener
import com.zhangqie.wanandroid.loading.ProgressDialogHandler
import com.zhangqie.wanandroid.tool.ProgressDialogUtil
import com.zhangqie.wanandroid.ui.user.login.LoginActivity
import kotlinx.android.synthetic.main.register_activity.*

/**
 * Created by zhangqie on 2019/2/22
 * Describe: 注册
 */
class RegisterActivity : BaseMvpActivity<IView<Any>,RegisterPresenter>(),IView<Any>,ProgressCancelListener{


    private var mProgressDialogHandler: ProgressDialogHandler? = null
    private var handler: Handler? = null

    override fun createPresenter(): RegisterPresenter {
        return RegisterPresenter()
    }

    override fun setMainLayout(): Int {
        return R.layout.register_activity
    }


    override fun initView() {
        handler = Handler()
        mProgressDialogHandler = ProgressDialogHandler(this,this,true)
    }

    override fun initBeforeData() {
        btn_register.setOnClickListener(onClickListener)
        tv_sign_in.setOnClickListener(onClickListener)
    }

    /**
     * OnClickListener
     */
    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_register -> {
                register()
            }
            R.id.tv_sign_in -> {
                Intent(this@RegisterActivity, LoginActivity::class.java).apply {
                    startActivity(this)
                }
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    /**
     * Register
     */
    private fun register() {
        if (validate()) {
            p!!.getRegisterLnt(et_username.text.toString(),
                    et_password.text.toString())
        }
    }

    /**
     * check data
     */
    private fun validate(): Boolean {
        var valid = true
        val username: String = et_username.text.toString()
        val password: String = et_password.text.toString()
        val password2: String = et_password2.text.toString()
        if (username.isEmpty()) {
            et_username.error = getString(R.string.username_not_empty)
            valid = false
        }
        if (password.isEmpty()) {
            et_password.error = getString(R.string.password_not_empty)
            valid = false
        }
        if (password2.isEmpty()) {
            et_password2.error = getString(R.string.confirm_password_not_empty)
            valid = false
        }
        if (password != password2) {
            et_password2.error = getString(R.string.password_cannot_match)
            valid = false
        }
        return valid
    }


    override fun onLoadContributorStart() {
        ProgressDialogUtil.showProgressDialog(mProgressDialogHandler)
    }

    override fun onLoadContribtorComplete(topContributor: Any?) {
        if (topContributor is LoginData)
        {
           var loginData: LoginData = topContributor as LoginData
            if (loginData.errorCode == 0){
                showToast(getString(R.string.register_success))
                handler!!.postDelayed(Runnable {
                    ProgressDialogUtil.dismissProgressDialog(mProgressDialogHandler)
                },1000)
                finish()
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





}