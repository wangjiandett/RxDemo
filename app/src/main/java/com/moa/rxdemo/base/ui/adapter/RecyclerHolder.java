package com.moa.rxdemo.base.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * ViewHolder 功能简单封装
 * <p>
 * Created by：wangjian on 2019/1/8 17:30
 */
public class RecyclerHolder<D> extends RecyclerView.ViewHolder {
    
    private SparseArray<View> views;
    
    public RecyclerHolder(@NonNull View itemView) {
        super(itemView);
        // 设置tag供点击的时候获取当前对象
        itemView.setTag(this);
        views = new SparseArray<>();
        initView();
    }
    
    protected <V extends View> V getView(int viewId){
        View view = views.get(viewId);
        if(view == null){
            view = itemView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (V) view;
    }
    
    /**
     * 初始化view组件
     */
    public void initView(){}
    
    /**
     * 数据和view组件进行绑定
     *
     * @param data 数据源
     */
    public void bind(D data){}
    
}
