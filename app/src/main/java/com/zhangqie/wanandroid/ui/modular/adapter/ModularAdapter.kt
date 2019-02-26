package com.zhangqie.wanandroid.ui.modular.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.zhangqie.wanandroid.R
import com.zhangqie.wanandroid.bean.ModularInfo

/**
 * Created by zhangqie on 2018/10/22
 * Describe:
 */
class ModularAdapter(context: Context, modularInfo: List<ModularInfo.DataBean>) : RecyclerView.Adapter<ModularAdapter.ViewHolder>(){

    var layoutInflater: LayoutInflater ?= null
    var modularInfo: List<ModularInfo.DataBean>? = null
    init {
        this.layoutInflater = LayoutInflater.from(context)
        this.modularInfo = modularInfo
    }

    interface OnItemClickListener{
        fun OnItemClick(position: Int)
    }

    var onItemClickListener: OnItemClickListener? =null


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        holder!!.title!!.text = modularInfo!![position].name
        var string: StringBuffer = StringBuffer()
        for (i in modularInfo!![position].children!!.indices){
            string.append(modularInfo!![position].children!![i].name+" ")
        }
        holder!!.title_second!!.text = string.toString()
        holder!!.rl_item!!.setOnClickListener {
                onItemClickListener!!.OnItemClick(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var view: View = layoutInflater!!.inflate(R.layout.item_modular_list,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return modularInfo!!.size
    }


    inner class ViewHolder(view : View): RecyclerView.ViewHolder(view){

        var title: TextView ? = null
        var title_second: TextView? = null
        var rl_item: RelativeLayout? = null
        init {
            title = view.findViewById(R.id.title_first)
            title_second = view.findViewById(R.id.title_second)
            rl_item = view.findViewById(R.id.rl_item)
        }
    }
}
