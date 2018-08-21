package com.moa.rxdemo.mvvm.base;

/**
 * loading status
 * <p>
 * Created by：wangjian on 2018/8/21 15:34
 */
public class LoadState {
    
    public static final int LOADED = 0; // load finish
    public static final int LOADING = 1; // loading
    public static final int LOADING_SUCCESS = 2; // load success
    public static final int LOADING_FAIL = 3; // load fail
    
    public String tipMsg;
    public int status;
    
    public LoadState(int status, String tipMsg) {
        this.status = status;
        this.tipMsg = tipMsg;
    }
    
    public static LoadState STATE_LOADED(){
        return new LoadState(LOADED, "");
    }
    
    public static LoadState STATE_LOADING(){
        return new LoadState(LOADING, "");
    }
    
    public static LoadState STATE_LOADING_SUCCESS(){
        return new LoadState(LOADING_SUCCESS, "");
    }
    
    public static LoadState STATE_LOADING_FAIL(String tipMsg){
        return new LoadState(LOADING_FAIL, tipMsg);
    }
    
}