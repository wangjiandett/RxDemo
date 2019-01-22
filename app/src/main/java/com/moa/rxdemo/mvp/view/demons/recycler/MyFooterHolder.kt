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

class MyFooterHolder(view: View) : RecyclerHolder<Int>(view) {


    private lateinit var textView: TextView
    lateinit var progressBar: ProgressBar

    override fun initView() {
        textView = this.getView(R.id.tv_text)
        progressBar = this.getView(R.id.progressBar)
    }

    override fun bind(data: Int) {
        progressBar.visibility = View.GONE
        if (data == Status.LOADING) {
            progressBar.visibility = View.VISIBLE
            textView.text = "加载中..."
        } else if (data == Status.FAIL) {
            textView.text = "加载失败，点击重试"
        } else if (data == Status.SUCCESS) {
            textView.text = "点击加载更多数据"
        } else if (data == Status.FINISH) {
            textView.text = "暂无更多数据"
        }
    }
}