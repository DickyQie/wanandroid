package com.zhangqie.wanandroid.ui

import android.content.Intent
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.view.KeyEvent
import android.widget.TextView
import com.gyf.barlibrary.ImmersionBar
import com.zhangqie.wanandroid.R
import com.zhangqie.wanandroid.base.BaseActivity
import com.zhangqie.wanandroid.tool.UtilFileDB
import com.zhangqie.wanandroid.ui.fragment.HomeFragment
import com.zhangqie.wanandroid.ui.fragment.ModularFragment
import com.zhangqie.wanandroid.ui.fragment.NavigationFragment
import com.zhangqie.wanandroid.ui.fragment.ProjectFragment
import com.zhangqie.wanandroid.ui.user.AboutActivity
import com.zhangqie.wanandroid.ui.user.MyContentActivity
import com.zhangqie.wanandroid.ui.user.login.LoginActivity
import com.zhangqie.wanandroid.widget.BottomNavigationViewHelper
import com.zhangqie.wanandroid.widget.DialogHelper
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.main_fragment.*

/**
 * Created by zhangqie on 2019/2/18
 * Describe:
 */
class MainFragment : BaseActivity() {

    var mExitTime: Long = 0
    internal val INTERVAL = 2000

    var mFragmentManager: FragmentManager? = null

    var homeFragment: HomeFragment? = null
    var modularFragment: ModularFragment? = null
    var navigationFragment: NavigationFragment? = null
    var projectFragment: ProjectFragment? = null


    /**
     * username TextView
     */
    private lateinit var nav_username: TextView

    override fun setMainLayout(): Int {
        return R.layout.main_fragment
    }

    override fun initView() {
        //在Activity里初始化
        ImmersionBar.with(this).navigationBarColor(R.color.colorPrimary).init()
        mFragmentManager = supportFragmentManager
        BottomNavigationViewHelper.disableShiftMode(bottom_navigation)
        bottom_navigation!!.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        showFragment(0)

    }

    override fun initBeforeData() {
        nav_view.run {
            setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)
            nav_username = getHeaderView(0).findViewById(R.id.tv_username)
        }
        nav_username.run {
            text = if (UtilFileDB.ISLOGIN()) {
                getString(R.string.login)
            } else {
                UtilFileDB.LOGINNAME()
            }
            setOnClickListener({
                if (UtilFileDB.ISLOGIN()) {
                    Intent(this@MainFragment, LoginActivity::class.java).run {
                        openForResyltActivity(this, 1)
                    }
                } else {

                }
            })
        }

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_home -> {
                showFragment(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_knowledge_system -> {
                showFragment(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_navigation -> {
                showFragment(2)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_project -> {
                showFragment(3)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    /***
     * 模块切换
     * @param page
     */
    private fun showFragment(page: Int) {
        val mFragmentTransaction = mFragmentManager!!.beginTransaction()
        if (homeFragment != null)
            mFragmentTransaction.hide(homeFragment!!)
        if (modularFragment != null)
            mFragmentTransaction.hide(modularFragment!!)
        if (navigationFragment != null)
            mFragmentTransaction.hide(navigationFragment!!)
        if (projectFragment != null)
            mFragmentTransaction.hide(projectFragment!!)
        when (page) {
            0 -> {
                if (homeFragment == null) {
                    homeFragment = HomeFragment()
                    mFragmentTransaction.add(R.id.main_content, homeFragment!!)
                } else {
                    mFragmentTransaction.show(homeFragment!!)
                }
            }
            1 -> {
                if (modularFragment == null) {
                    modularFragment = ModularFragment()
                    mFragmentTransaction.add(R.id.main_content, modularFragment!!)
                } else {
                    mFragmentTransaction.show(modularFragment!!)
                }
            }
            2 -> {
                if (navigationFragment == null) {
                    navigationFragment = NavigationFragment()
                    mFragmentTransaction.add(R.id.main_content, navigationFragment!!)
                } else {
                    mFragmentTransaction.show(navigationFragment!!)
                }
            }
            3 -> {
                if (projectFragment == null) {
                    projectFragment = ProjectFragment()
                    mFragmentTransaction.add(R.id.main_content, projectFragment!!)
                } else {
                    mFragmentTransaction.show(projectFragment!!)
                }
            }
        }
        mFragmentTransaction.commitAllowingStateLoss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == 2) {
            nav_username.text = UtilFileDB.LOGINNAME()
        }
    }


    /**
     * NavigationView 监听
     */
    private val onDrawerNavigationItemSelectedListener =
            NavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_camera -> {
                        if (UtilFileDB.ISLOGIN()) {
                            showToast(resources.getString(R.string.login_tint))
                            Intent(this@MainFragment, LoginActivity::class.java).run {
                                openForResyltActivity(this, 1)
                            }
                        } else {
                            Intent(this@MainFragment, MyContentActivity::class.java).run {
                                startActivity(this)
                            }
                        }
                        // drawer_layout.closeDrawer(GravityCompat.START)
                    }
                    R.id.nav_setting -> {
                        showToast("开发中......")
                        /*   Intent(this@MainFragment, SettingActivity::class.java).run {
                               // putExtra(Constant.TYPE_KEY, Constant.Type.SETTING_TYPE_KEY)
                               startActivity(this)
                           }*/
                        // drawer_layout.closeDrawer(GravityCompat.START)
                    }
                    R.id.nav_about_us -> {
                        Intent(this@MainFragment, AboutActivity::class.java).run {
                            startActivity(this)
                        }

                    }
                    R.id.nav_logout -> {
                        if (UtilFileDB.ISLOGIN()) {
                            showToast(resources.getString(R.string.login_tint))
                        } else {
                            DialogHelper.getConfirmDialog(this, "是否退出当前用户") { dialog, which ->
                                UtilFileDB.DELETESHAREDDATA("wanusername")
                                nav_username.text = getString(R.string.login)
                                showToast(R.string.logout_success)

                            }.show()
                        }
                        // drawer_layout.closeDrawer(GravityCompat.START)
                    }

                }
                true
            }


    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (event!!.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event!!.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                if (drawer_layout!!.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout!!.closeDrawer(GravityCompat.START)
                } else {
                    if (System.currentTimeMillis() - mExitTime > INTERVAL) {
                        showToast("再按一次退出程序")
                        mExitTime = System.currentTimeMillis()
                    } else {
                        setResult(1)
                        finish()
                    }
                }
            }
            return true
        }
        return super.dispatchKeyEvent(event)
    }

    override fun onDestroy() {
        super.onDestroy()
        ImmersionBar.with(this).destroy()
    }

}