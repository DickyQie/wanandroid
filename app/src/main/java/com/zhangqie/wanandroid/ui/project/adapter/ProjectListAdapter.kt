package com.zhangqie.wanandroid.ui.project.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.zhangqie.wanandroid.R
import com.zhangqie.wanandroid.bean.ProjectListInfo
import com.zhangqie.wanandroid.callback.OnItemClickListenter

/**
 * Created by zhangqie on 2019/2/22
 * Describe:
 */
class ProjectListAdapter(context: Context?, datas: MutableList<ProjectListInfo.DataBean.DatasBean>) : RecyclerView.Adapter<ProjectListAdapter.ViewHolder>() {

    var datas: MutableList<ProjectListInfo.DataBean.DatasBean>? = null
    var layoutInflater: LayoutInflater? = null
    var context: Context? = null

    var onItemClickListener: OnItemClickListenter? = null

    fun setOnItemClickListenter(onItemClickListenter: OnItemClickListenter) {
        this.onItemClickListener = onItemClickListenter
    }

    init {
        this.datas = datas
        this.layoutInflater = LayoutInflater.from(context)
        this.context = context
    }

    override fun getItemCount(): Int {
        return datas!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var view: View = layoutInflater!!.inflate(R.layout.item_project_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var dataBean: ProjectListInfo.DataBean.DatasBean = datas!!.get(position)
        holder!!.item_project_list_title_tv!!.text = Html.fromHtml(dataBean.title)
        holder!!.item_project_list_content_tv!!.text = Html.fromHtml(dataBean.desc)
        holder!!.item_project_list_author_tv!!.text = dataBean.author
        holder.item_project_list_time_tv!!.text = dataBean.niceDate
        Glide.with(holder.item_project_list_iv!!.context)
                .load(dataBean.envelopePic)
                .placeholder(R.mipmap.default_img)
                .error(R.mipmap.default_img)
                .centerCrop()
                .dontAnimate()
                .into(holder.item_project_list_iv)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var item_project_list_iv: ImageView? = null
        var item_project_list_title_tv: TextView? = null
        var item_project_list_content_tv: TextView? = null
        var item_project_list_author_tv: TextView? = null
        var item_project_list_time_tv: TextView? = null

        init {
            item_project_list_iv = view.findViewById(R.id.item_project_list_iv)
            item_project_list_title_tv = view.findViewById(R.id.item_project_list_title_tv)
            item_project_list_content_tv = view.findViewById(R.id.item_project_list_content_tv)
            item_project_list_author_tv = view.findViewById(R.id.item_project_list_author_tv)
            item_project_list_time_tv = view.findViewById(R.id.item_project_list_time_tv)
            itemView.setOnClickListener {
                if (onItemClickListener != null){
                    onItemClickListener!!.onItemClick(view,adapterPosition)
                }
            }
        }
    }
}