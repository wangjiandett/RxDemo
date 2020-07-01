package com.moa.baselib.base.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class ViewHolder<T> {

    private long id;

    protected View mView;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public abstract int getLayoutId();

    protected View init(T data, ViewGroup viewGroup, Context context){
        mView = inflateLayout(context, getLayoutId(), viewGroup);
        initView(mView, data, context);
        return mView;
    }

    public void unbind(boolean full) {

    }

    public View inflateLayout(Context context, int layoutId, ViewGroup parent){
        // 此处使用LayoutInflater.inflate，传入parent
        // 防止使用View.inflate导致item顶层布局设置的布局参数无效
        return LayoutInflater.from(context).inflate(layoutId, parent, false);
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V findView(View view, int viewId){
        return (V)view.findViewById(viewId) ;
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V findView(int viewId){
        return (V)mView.findViewById(viewId) ;
    }

    public abstract void initView(View itemView, T data, Context context);

    public abstract void bindData(T data, int position, Context context);

}
