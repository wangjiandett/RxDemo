package com.moa.rxdemo.mvp.view.demons.recycler

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.moa.rxdemo.R
import com.moa.rxdemo.view.recycler.HFRecyclerAdapter
import com.moa.rxdemo.view.recycler.RecyclerAdapter
import com.moa.rxdemo.view.recycler.RecyclerHolder

class MyHFRecyclerAdapter(recyclerView: RecyclerView, var fragment: RefreshRecyclerFragment) : HFRecyclerAdapter<Data>(recyclerView) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerHolder<*> {
        return when (viewType) {
            ITEM_TYPE_HEADER -> MyHeaderHolder(getItemView(viewGroup, R.layout.tt_item_recycler_header))
            ITEM_TYPE_FOOTER -> MyFooterHolder(getItemView(viewGroup, R.layout.tt_item_recycler_footer))
            ITEM_TYPE_EMPTY -> {
                val holder = EmptyHolder(getItemView(viewGroup, R.layout.tt_item_recycler_empty))
                holder.adapter = this
                return holder
            }
            else -> MyRecyclerHolder(getItemView(viewGroup, R.layout.tt_item_recycler))
        }
    }

    override fun refresh() {
        fragment.refresh()
    }
}