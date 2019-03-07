package com.moa.rxdemo.mvp.view.demons.recycler

import android.view.View
import android.widget.TextView
import com.moa.baselib.view.recycler.RecyclerHolder
import com.moa.rxdemo.R

/**
 * 类或文件描述
 *
 * Created by：wangjian on 2019/1/9 11:33
 */

open class MyRecyclerHolder(view: View): RecyclerHolder<Data>(view){


    private lateinit var textView: TextView

    override fun initView() {
        textView = getView(R.id.tv_text)
    }

    override fun bind(data: Data) {
        textView.text = data.data
    }

}