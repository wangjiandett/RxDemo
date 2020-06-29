package com.moa.baselib.base.net.mvp;

import com.moa.baselib.base.net.ProgressResultListener;

/**
 * value call back
 * <p>
 * Created by：wangjian on 2017/12/21 11:12
 */
public interface ValueCallback<T> extends ProgressResultListener {
    
    void onShowProgress();
    
    void onHideProgress();
    
    void onSuccess(T value);
    
    void onFail(String msg);
    
}
