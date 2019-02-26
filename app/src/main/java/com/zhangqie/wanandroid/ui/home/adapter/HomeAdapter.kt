package com.zhangqie.wanandroid.ui.adapter

import android.content.Context
import android.content.Intent
import android.database.DataSetObservable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.bingoogolapple.bgabanner.BGABanner
import com.zhangqie.wanandroid.R
import com.zhangqie.wanandroid.bean.BannerInfo
import com.bumptech.glide.Glide
import com.zhangqie.wanandroid.bean.HomeInfo
import com.zhangqie.wanandroid.callback.OnItemClickListenter
import com.zhangqie.wanandroid.ui.web.WebContentActivity


/**
 * Created by zhangqie on 2018/9/16
 * Describe: 首页Adapter
 */
class HomeAdapter(context: Context,bannerInfo: List<BannerInfo.DataBean>,datalist: List<HomeInfo.DataBean.DatasBean>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var layoutInflater: LayoutInflater ? = null
    var bannerInfo: List<BannerInfo.DataBean> ? = null
    var datalist: List<HomeInfo.DataBean.DatasBean> ?= null

    val ITEM_VIEW_TYPE_HEADER : Int = 0
    val ITEM_VIEW_TYPE_ITEM : Int = 1

    var onItemClickListener: OnItemClickListenter? = null

    fun setOnItemClickListenter(onItemClickListenter: OnItemClickListenter) {
        this.onItemClickListener = onItemClickListenter
    }


    init {
        this.layoutInflater = LayoutInflater.from(context)
        this.bannerInfo = bannerInfo
        this.datalist = datalist
    }


    override fun getItemCount(): Int {
        return datalist!!.size+1
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(isHeander(position)){
            var viewHandlerHolder: ViewHandlerHolder = (holder as ViewHandlerHolder)
            if(viewHandlerHolder != null){
               viewHandlerHolder.bagBGABannerr!!.setAdapter(BGABanner.Adapter<ImageView, String> { banner, itemView, model, position ->
                    Glide.with(viewHandlerHolder.bagBGABannerr!!.context)
                            .load(model)
                            .placeholder(R.mipmap.default_img)
                            .error(R.mipmap.default_img)
                            .centerCrop()
                            .dontAnimate()
                            .into(itemView)
                })

                var listImg = mutableListOf<String>()
                var listTitle = mutableListOf<String>()
                for (i in bannerInfo!!.indices) {
                   listImg.add(bannerInfo!!.get(i).imagePath!!)
                   listTitle.add(bannerInfo!![i].title!!)
                }
                viewHandlerHolder.bagBGABannerr!!.setData(listImg,
                        listTitle)
                viewHandlerHolder.bagBGABannerr!!.setDelegate(BGABanner.Delegate<ImageView, String>{ banner, itemView, model, position ->
                    var intent: Intent = Intent(viewHandlerHolder.bagBGABannerr!!.context,WebContentActivity::class.java)
                    intent.putExtra("title",listTitle[position])
                    intent.putExtra("link",bannerInfo!![position].url)
                    viewHandlerHolder.bagBGABannerr!!.context.startActivity(intent)
                })
            }

        }else{
            var dataBean: HomeInfo.DataBean.DatasBean = datalist!!.get(position-1)
            if (dataBean.isFresh){
                (holder as ViewHolder)!!.tv_article_fresh!!.visibility= View.VISIBLE
            }
            if (dataBean.chapterName.isNotEmpty()){
                (holder as ViewHolder)!!.tv_article_chapterName!!.setText(dataBean.chapterName)
            }else{
                (holder as ViewHolder)!!.tv_article_chapterName!!.visibility = View.INVISIBLE
            }
            (holder as ViewHolder)!!.title!!.setText(dataBean.title)
            (holder as ViewHolder)!!.tv_article_author!!.setText(dataBean.author)
            (holder as ViewHolder)!!.tv_article_date!!.setText(dataBean.niceDate)

            Glide.with(holder.iv_article_thumbnail!!.context)
                    .load(datalist!!.get(position-1).envelopePic)
                    .placeholder(R.mipmap.default_img)
                    .error(R.mipmap.default_img)
                    .centerCrop()
                    .dontAnimate()
                    .into(holder.iv_article_thumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == ITEM_VIEW_TYPE_HEADER){
            var view: View = layoutInflater!!.inflate(R.layout.item_handler_banner,parent,false)
            return ViewHandlerHolder(view)
        }else{
            var view: View = layoutInflater!!.inflate(R.layout.item_home_list,parent,false)
            return ViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isHeander(position)) ITEM_VIEW_TYPE_HEADER else ITEM_VIEW_TYPE_ITEM
    }

    fun addData(datalistn: List<HomeInfo.DataBean.DatasBean>){
         datalist!!.containsAll(datalistn)
         notifyDataSetChanged()
         notifyListDataSetChanged()
        // notifyItemInserted(datalist!!.size)//添加操作的动画
    }

    fun notifyListDataSetChanged() {
        mDataSetObservable.notifyChanged()
    }

    private val mDataSetObservable = DataSetObservable()

    fun isHeander(position: Int): Boolean {
        return position == 0
    }


    inner class ViewHandlerHolder(view: View) : RecyclerView.ViewHolder(view){
        var bagBGABannerr : BGABanner ?= null
        init {
            bagBGABannerr = view.findViewById(R.id.banner_content)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var title : TextView ?= null
        var tv_article_fresh : TextView ?= null
        var tv_article_author : TextView ?= null
        var tv_article_date : TextView ?= null
        var iv_article_thumbnail : ImageView ?= null
        var tv_article_chapterName : TextView ?= null
        init {
            title = view.findViewById(R.id.tv_article_title)
            tv_article_fresh = view.findViewById(R.id.tv_article_fresh)
            tv_article_author = view.findViewById(R.id.tv_article_author)
            tv_article_date = view.findViewById(R.id.tv_article_date)
            iv_article_thumbnail = view.findViewById(R.id.iv_article_thumbnail)
            tv_article_chapterName = view.findViewById(R.id.tv_article_chapterName)
            itemView.setOnClickListener {
                if (onItemClickListener != null){
                    onItemClickListener!!.onItemClick(view,adapterPosition)
                }
            }
        }
    }

}
