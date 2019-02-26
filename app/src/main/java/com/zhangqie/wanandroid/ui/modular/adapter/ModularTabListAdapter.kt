package com.zhangqie.wanandroid.ui.modular.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.zhangqie.wanandroid.R
import com.zhangqie.wanandroid.bean.ModularListInfo
import com.zhangqie.wanandroid.bean.ProjectListInfo
import com.zhangqie.wanandroid.callback.OnItemClickListenter

/**
 * Created by zhangqie on 2019/2/22
 * Describe:
 */
class ModularTabListAdapter(context: Context?, datas: MutableList<ModularListInfo.DataBean.DatasBean>) : RecyclerView.Adapter<ModularTabListAdapter.ViewHolder>() {

    var datas: MutableList<ModularListInfo.DataBean.DatasBean>? = null
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
        var view: View = layoutInflater!!.inflate(R.layout.item_modular_tab_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var dataBean: ModularListInfo.DataBean.DatasBean = datas!!.get(position)
        holder!!.tv_article_title!!.text = dataBean.title
        holder!!.tv_article_chapterName!!.text = dataBean.chapterName
        holder!!.tv_article_author!!.text = dataBean.author
        holder!!.tv_article_date!!.text = dataBean.niceDate
        if (TextUtils.isEmpty(dataBean.envelopePic)) {
            holder!!.iv_article_thumbnail!!.visibility = View.GONE
        }else {
            Glide.with(holder.iv_article_thumbnail!!.context)
                    .load(dataBean.envelopePic)
                    .placeholder(R.mipmap.default_img)
                    .error(R.mipmap.default_img)
                    .centerCrop()
                    .dontAnimate()
                    .into(holder.iv_article_thumbnail)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_article_title: TextView? = null
        var tv_article_chapterName: TextView? = null
        var tv_article_author: TextView? = null
        var tv_article_date: TextView? = null
        var iv_article_thumbnail: ImageView? = null
        init {
            tv_article_title = view.findViewById(R.id.tv_article_title)
            tv_article_chapterName = view.findViewById(R.id.tv_article_chapterName)
            tv_article_author = view.findViewById(R.id.tv_article_author)
            tv_article_date = view.findViewById(R.id.tv_article_date)
            iv_article_thumbnail = view.findViewById(R.id.iv_article_thumbnail)
            itemView.setOnClickListener {
                if (onItemClickListener != null){
                    onItemClickListener!!.onItemClick(view,adapterPosition)
                }
            }
        }
    }
}