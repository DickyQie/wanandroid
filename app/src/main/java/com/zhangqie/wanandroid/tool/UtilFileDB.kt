package com.zhangqie.wanandroid.tool

import com.zhangqie.wanandroid.base.BaseApplication
import android.app.Activity



/**
 * Created by zhangqie on 2016/6/5 0005
 *
 *
 * 基本数据操作类(微量缓存数据)
 *
 */

object UtilFileDB {

    var sharedPreferences = BaseApplication.preferences
    var editor = BaseApplication.editor

    /**
     * 验证是否登录并获取相关信息
     * @return
     */
    fun ISLOGIN(): Boolean {
        return if (sharedPreferences!!.getString("wanusername", "") == "") {
            true
        } else false
    }

    /****
     * 永久缓存
     * @param key
     * @param content
     */
    fun ADDSHAREDDATA(key: String, content: String) {
        editor!!.putString(key, content)
        editor!!.commit()
    }

    /****
     * 永久缓存
     * @param key
     * @param content
     */
    fun ADDSHAREDDATA(key: String, content: Int) {
        editor!!.putInt(key, content)
        editor!!.commit()
    }

    /***
     * 清空
     * @param key
     */
    fun DELETESHAREDDATA(key: String) {
        editor!!.remove(key)
        editor!!.commit()
    }

    /***
     * 查询数据
     * @param key
     * @return
     */
    fun SELECTSHAREDDATA(key: String): String {
        val content = sharedPreferences!!.getString(key, "")
        return if (content != "") {
            content
        } else ""
    }

    /***
     * 用户名
     * @return
     */
    fun LOGINNAME(): String {
        val loginName = sharedPreferences!!.getString("wanusername", "")
        return if (loginName != "") {
            loginName
        } else "未登录"
    }


    /****
     * 时间戳
     * @return
     */
    fun currentTimeMillis(): String {
        return System.currentTimeMillis().toString()
    }

    fun currentTimeLongMillis(time: Long?): Long {
        return (System.currentTimeMillis() - time!!) / 1000
    }


    /****
     * 获取版本号
     * @param activity
     * @return
     */
    fun getVersion(activity: Activity): String {
        try {
            val manager = activity.packageManager
            val info = manager.getPackageInfo(activity.packageName, 0)
            return "V " + info.versionName+"."+info.versionCode
        } catch (e: Exception) {
            return "V 1.0.0"
        }

    }


}
