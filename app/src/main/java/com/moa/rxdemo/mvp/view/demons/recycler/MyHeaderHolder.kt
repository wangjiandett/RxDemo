package com.moa.rxdemo.mvp.view.demons.recycler

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.moa.rxdemo.R
import com.moa.rxdemo.view.recycler.RecyclerHolder
import com.moa.rxdemo.view.recycler.Status

/**
 * 类或文件描述
 *
 * Created by：wangjian on 2019/1/9 11:33
 */

class MyHeaderHolder(view: View) : RecyclerHolder<String>(view) {


    private lateinit var textView: TextView
    lateinit var progressBar: ProgressBar

    override fun initView() {
        textView = this.getView(R.id.tv_text)
    }

    override fun bind(data: String?) {
        textView.text = "header:$data"
    }
}