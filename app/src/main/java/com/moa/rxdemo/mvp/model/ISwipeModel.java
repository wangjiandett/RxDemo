package com.moa.rxdemo.mvp.model;

import com.moa.baselib.base.net.ValueCallback;
import com.moa.rxdemo.mvp.bean.SwipeItem;

import java.util.List;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2017/12/21 10:59
 */
public interface ISwipeModel {
    
    void loadSwipeList(int page, ValueCallback<List<SwipeItem>> callback);
}
