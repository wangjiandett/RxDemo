package com.moa.rxdemo.view.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.moa.rxdemo.R;
import com.moa.rxdemo.utils.AppUtils;

/**
 * ViewHolder 功能简单封装
 * <p>
 * Created by：wangjian on 2019/1/8 17:30
 */
public abstract class RecyclerHolder<D> extends RecyclerView.ViewHolder {
    
    private SparseArray<View> views;
    private RecyclerAdapter adapter;
    
    public RecyclerHolder(@NonNull View itemView) {
        super(itemView);
        // 设置tag供点击事件的时候获取当前对象
        itemView.setTag(this);
        views = new SparseArray<>();
        initSelector();
        initView();
    }

    public RecyclerHolder(@NonNull View itemView, RecyclerAdapter adapter) {
        this(itemView);
        this.adapter = adapter;
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
     * s设置item点击事件监听
     *
     * @param onClickListener
     */
    public void setOnClickListener(View.OnClickListener onClickListener){
        this.itemView.setOnClickListener(onClickListener);
    }

    /**
     * 设置item长按事件监听
     *
     * @param onLongClickListener
     */
    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener){
        this.itemView.setOnLongClickListener(onLongClickListener);
    }

    /**
     * 初始化bg selector
     */
    protected void initSelector(){
        itemView.setBackground(AppUtils.getDrawable(itemView.getContext(), R.drawable.tt_item_selector));
    }

    public RecyclerAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(RecyclerAdapter adapter) {
        this.adapter = adapter;
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

    public void unbind(){
        views.clear();
    }
    
}
