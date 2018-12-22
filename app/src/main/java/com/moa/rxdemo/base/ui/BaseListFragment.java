package com.moa.rxdemo.base.ui;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;

import com.moa.rxdemo.base.ui.adapter.HolderAdapter;
import com.moa.rxdemo.base.ui.adapter.ViewHolder;

/**
 * 封装列表适配器功能，适用于ListView和GridView
 *
 * <p>
 * Created by：wangjian on 2018/12/22 16:32
 */
public abstract class BaseListFragment<T> extends BaseFragment {
    
    protected DataListAdapter mDataListAdapter;
    
    @Override
    protected void initView(View view) {
        super.initView(view);
        mDataListAdapter = new DataListAdapter(getActivity());
    }
    
    protected void bindAdapter(AbsListView listView) {
        listView.setAdapter(mDataListAdapter);
    }
    
    protected class DataListAdapter extends HolderAdapter<T> {
        
        DataListAdapter(Context context) {
            super(context);
        }
        
        @Override
        protected ViewHolder<T> createHolder(int position, T obj) {
            return getItemHolder();
        }
    }
    
    protected abstract ViewHolder<T> getItemHolder();
    
}
