package com.zhangqie.wanandroid.bean

/**
 * Created by zhangqie on 2019/2/22
 * Describe:
 */
class ProjectListInfo : BaseBean() {


    var data: DataBean? = null

    class DataBean {
        var curPage: Int = 0
        var offset: Int = 0
        var isOver: Boolean = false
        var pageCount: Int = 0
        var size: Int = 0
        var total: Int = 0
        var datas: List<DatasBean>? = null

        class DatasBean {
            /**
             * apkLink :
             * author : kingwang666
             * chapterId : 294
             * chapterName : 完整项目
             * collect : false
             * courseId : 13
             * desc : APP信息是一个免费的工具应用. 它有以下功能特点:
             *
             * 查看已安装的app信息.
             * 查看未安装的apk信息.
             * 导出已安装的app应用的apk文件.
             * 复制apk的签名信息到剪切板.
             * envelopePic : http://wanandroid.com/blogimgs/f16b7060-38e2-4ebd-87d9-d61b59a000e2.png
             * fresh : false
             * id : 7892
             * link : http://www.wanandroid.com/blog/show/2493
             * niceDate : 2019-01-23
             * origin :
             * projectLink : https://github.com/kingwang666/GetApk
             * publishTime : 1548247914000
             * superChapterId : 294
             * superChapterName : 开源项目主Tab
             * tags : [{"name":"项目","url":"/project/list/1?cid=294"}]
             * title : 一个可以显示app或者apk信息。并且可导出已安装的app的apk文件工具应用
             * type : 0
             * userId : -1
             * visible : 1
             * zan : 0
             */

            var apkLink: String? = null
            var author: String? = null
            var chapterId: Int = 0
            var chapterName: String? = null
            var isCollect: Boolean = false
            var courseId: Int = 0
            var desc: String? = null
            var envelopePic: String? = null
            var isFresh: Boolean = false
            var id: Int = 0
            var link: String? = null
            var niceDate: String? = null
            var origin: String? = null
            var projectLink: String? = null
            var publishTime: Long = 0
            var superChapterId: Int = 0
            var superChapterName: String? = null
            var title: String? = null
            var type: Int = 0
            var userId: Int = 0
            var visible: Int = 0
            var zan: Int = 0
            var tags: List<TagsBean>? = null

            class TagsBean {
                /**
                 * name : 项目
                 * url : /project/list/1?cid=294
                 */

                var name: String? = null
                var url: String? = null
            }
        }
    }
}
