package com.moa.rxdemo.mvp.view.demons.recycler

import android.content.Context
import com.moa.rxdemo.R
import com.moa.rxdemo.base.ui.adapter.RecyclerAdapter

/**
 * 类或文件描述
 *
 * Created by：wangjian on 2019/1/9 11:46
 */
class MyRecyclerAdapter : RecyclerAdapter<String, MyRecyclerHolder>() {


    override fun getHolder(context: Context?, viewType: Int): MyRecyclerHolder {
        return MyRecyclerHolder(getLayout(context, R.layout.tt_item_recycler))
    }

    override fun onBindViewHolder(data: String?, position: Int, holder: MyRecyclerHolder?) {
        holder?.bind(data)
    }
}