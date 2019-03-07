package com.moa.rxdemo.mvp.view.demons.recycler

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.moa.baselib.view.recycler.RecyclerHolder
import com.moa.baselib.view.recycler.Status
import com.moa.rxdemo.R

/**
 * 类或文件描述
 *
 * Created by：wangjian on 2019/1/9 11:33
 */

class EmptyHolder(view: View) : RecyclerHolder<Int>(view) {


    private lateinit var textView: TextView
    lateinit var progressBar: ProgressBar

    override fun initView() {
        textView = this.getView(R.id.tv_text)
    }

    override fun bind(data: Int?) {

        val textString = when (data) {
            Status.LOADING ->  "加载中..."
            Status.FAIL -> "加载失败，点击重试"
            Status.FINISH ->  "暂无数据,请点击重试"
            else ->  "请点击加载"//加载成功
        }

        textView.text = textString

        textView.setOnClickListener {

            if(adapter != null){
                adapter.refresh()
            }
        }

    }
}