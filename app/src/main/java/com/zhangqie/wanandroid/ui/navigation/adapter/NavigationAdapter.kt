package com.zhangqie.wanandroid.ui.navigation.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.cxz.wanandroid.utils.CommonUtil
import com.zhangqie.wanandroid.R
import com.zhangqie.wanandroid.bean.NavigationInfo
import com.zhangqie.wanandroid.tool.DisplayUtil
import com.zhangqie.wanandroid.ui.web.WebContentActivity
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

/**
 * Created by zhangqie on 2019/2/21
 * Describe:
 */

class NavigationAdapter(context: Context?, datas: MutableList<NavigationInfo.DataBean>) : RecyclerView.Adapter<NavigationAdapter.ViewHolder>() {

    var datas: MutableList<NavigationInfo.DataBean>? = null
    var layoutInflater: LayoutInflater? = null
    var context: Context? = null

    init {
        this.datas = datas
        this.layoutInflater = LayoutInflater.from(context)
        this.context = context
    }

    override fun getItemCount(): Int {
        return datas!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var view: View = layoutInflater!!.inflate(R.layout.item_navigation_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.title!!.text = datas!!.get(position).name
        val articles: List<NavigationInfo.DataBean.ArticlesBean> = datas!!.get(position).articles

        val adapter: TagAdapter<NavigationInfo.DataBean.ArticlesBean> = object : TagAdapter<NavigationInfo.DataBean.ArticlesBean>(articles) {
            override fun getView(parent: FlowLayout?, position: Int, article: NavigationInfo.DataBean.ArticlesBean?): View? {

                val tv: TextView = LayoutInflater.from(parent?.context).inflate(R.layout.flow_layout_tv,
                        holder!!.tagFlowLayout, false) as TextView

                article ?: return null

                val padding: Int = DisplayUtil.dip2px(context,10f)
                tv.setPadding(padding, padding, padding, padding)
                tv.text = article.title
                tv.setTextColor(CommonUtil.randomColor())
                return tv
            }
        }
        holder!!.tagFlowLayout!!.adapter = adapter

        holder!!.tagFlowLayout!!.setOnTagClickListener( TagFlowLayout.OnTagClickListener { view, position, parent ->
            var data: NavigationInfo.DataBean.ArticlesBean = articles[position]
            Toast.makeText(context,data.title, Toast.LENGTH_LONG).show()
            var intent: Intent = Intent(context, WebContentActivity::class.java)
            intent.putExtra("title",data.title)
            intent.putExtra("link",data.link)
            context!!.startActivity(intent)
            return@OnTagClickListener false

        })
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView? = null
        var tagFlowLayout: TagFlowLayout? = null

        init {
            title = view.findViewById(R.id.item_navigation_tv)
            tagFlowLayout = view.findViewById(R.id.item_navigation_flow_layout)
        }
    }
}