package com.moa.rxdemo.base.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moa.rxdemo.utils.ToastUtils;

/**
 * BaseFragment基类
 * <p>
 * Created by：wangjian on 2017/12/22 13:55
 */
public abstract class BaseFragment extends Fragment {
    
    private boolean isRootFragment;
    private String title;
    private int titleRes;
    private String subtitle;
    private boolean showTitle = true;
    private boolean homeAsUp = false;
    private boolean showHome = false;
    private boolean showCustom = false;
    
    protected View mView;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // 1.加载页面布局
        mView = inflater.inflate(getLayoutId(), null);
        
        // 2.init header
        initHeader();
        // 3.init view
        initView(mView);
        // 4.init data
        initData();
        
        return mView;
    }
    
    /**
     * layout id
     *
     * @return
     */
    protected abstract int getLayoutId();
    
    /**
     * init titlebar
     */
    protected void initHeader() {
    }
    
    /**
     * init layout view
     */
    protected void initView(View view) {
    }
    
    /**
     * Deal data
     */
    protected void initData() {
    }
    
    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(@IdRes int id) {
        return (T) mView.findViewById(id);
    }
    
    @SuppressWarnings("unchecked")
    protected <T extends View> T $(View parent, @IdRes int id) {
        return (T) parent.findViewById(id);
    }
    
    public void setRootFragment(boolean rootFragment) {
        isRootFragment = rootFragment;
        setHasOptionsMenu(rootFragment);
    }
    
    public void setTitle(String title) {
        this.title = title;
        this.titleRes = 0;
        
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }
    
    public void setTitle(int titleRes) {
        this.title = null;
        this.titleRes = titleRes;
        
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(titleRes);
        }
    }
    
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(subtitle);
        }
    }
    
    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
        
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(showTitle);
        }
    }
    
    public void setHomeAsUp(boolean homeAsUp) {
        this.homeAsUp = homeAsUp;
        
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(homeAsUp);
        }
    }
    
    public void setShowHome(boolean showHome) {
        this.showHome = showHome;
        
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(showHome);
        }
    }
    
    public void setShowCustom(boolean showCustom) {
        this.showCustom = showCustom;
        
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(showCustom);
        }
    }
    
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isRootFragment) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                if (titleRes != 0) {
                    actionBar.setTitle(titleRes);
                } else {
                    actionBar.setTitle(title);
                }
                actionBar.setSubtitle(subtitle);
                actionBar.setDisplayShowCustomEnabled(showCustom);
                actionBar.setDisplayHomeAsUpEnabled(homeAsUp);
                actionBar.setDisplayShowHomeEnabled(showHome);
                actionBar.setDisplayShowTitleEnabled(showTitle);
                onConfigureActionBar(actionBar);
            }
        }
    }
    
    public void onConfigureActionBar(ActionBar actionBar) {
    
    }
    
    @Nullable
    public ActionBar getSupportActionBar() {
        FragmentActivity activity = getActivity();
        if (activity instanceof AppCompatActivity) {
            AppCompatActivity compatActivity = (AppCompatActivity) activity;
            return compatActivity.getSupportActionBar();
        }
        return null;
    }
    
    protected void showToast(String text){
        ToastUtils.showToast(getActivity(), text);
    }
}
