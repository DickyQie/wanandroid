package com.zhangqie.wanandroid.api

/**
 * Created by zhangqie on 2018/10/15
 * Describe:
 */
class Api{

    companion object {


        var wanUrl: String = " http://www.wanandroid.com/"

        //http://www.wanandroid.com/article/list/0/json
        var pageData: String = "http://www.wanandroid.com/article/list/"

        var modularUrl: String ="http://www.wanandroid.com/tree/json"

        /***
         * 导航
         */
        var navigationUrl: String = "http://www.wanandroid.com/navi/json"

        /**
         * id key
         */
        const val CONTENT_CID_KEY = "cid"
    }

}
