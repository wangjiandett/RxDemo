package com.moa.rxdemo.mvp.view.demons.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.moa.rxdemo.R
import com.moa.rxdemo.view.recycler.IData
import com.moa.rxdemo.view.recycler.RecyclerAdapter
import com.moa.rxdemo.view.recycler.RecyclerHolder

/**
 * 类或文件描述
 *
 * Created by：wangjian on 2019/1/9 11:46
 */
class ManagerAdapter: RecyclerAdapter<Data>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerHolder<*> {
        // 根据不同的viewType返回不同的holder
        return if(viewType == ITEM_TYPE_EMPTY){
            EmptyHolder(getItemView(viewGroup, R.layout.tt_item_recycler_empty))
        }else{
            MyRecyclerHolder(getItemView(viewGroup, R.layout.tt_item_recycler))
        }
    }
}