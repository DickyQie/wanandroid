package com.zhangqie.wanandroid.config

/**
 * 常量类
 */
interface Constants {
    companion object {

        //http://www.tngou.net/api/top/list?page=1&rows=10

        //    http://zmxy.blibao.com/interface/getOrderDetail.htm?shopperId=9356&machineId=5117&orderType=2&orderId=108
        val TG_ULR = "http://www.tngou.net/api/top/"
        val GTIHUB_ULR = "http://zmxy.blibao.com/"

        val REFRESH = "refresh"//刷新

        val RESULTDATA = "resultData"//返回数据


        val HTTPURL = "https://api.github.com"

        /**
         * title key
         */
        const val CONTENT_TITLE_KEY = "title"

        /**
         * content data key
         */
        const val CONTENT_DATA_KEY = "content_data"

        /**
         * id key
         */
        const val CONTENT_CID_KEY = "cid"
    }

}
