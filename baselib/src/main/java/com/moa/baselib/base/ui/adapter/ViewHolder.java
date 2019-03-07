package com.moa.baselib.base.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public abstract class ViewHolder<T> {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public abstract View init(T data, ViewGroup viewGroup, Context context);

    public abstract void bind(T data, int position, Context context);

    public void unbind(boolean full) {

    }

    public View getLayout(Context context, int layoutId){
        return View.inflate(context, layoutId, null);
    }
}
