package com.zhangqie.wanandroid.bean

/**
 * Created by zhangqie on 2019/2/22
 * Describe:
 */
class LoginData : BaseBean() {


    var data: DataBean? = null

    class DataBean {
        /**
         * chapterTops : []
         * collectIds : []
         * email :
         * icon :
         * id : 17716
         * password :
         * token :
         * type : 0
         * username : zhangqie
         */

        var email: String? = null
        var icon: String? = null
        var id: Int = 0
        var password: String? = null
        var token: String? = null
        var type: Int = 0
        var username: String? = null
        var chapterTops: List<*>? = null
        var collectIds: List<*>? = null
    }
}
