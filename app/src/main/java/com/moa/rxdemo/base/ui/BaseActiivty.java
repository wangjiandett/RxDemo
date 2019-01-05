package com.moa.rxdemo.base.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moa.rxdemo.App;
import com.moa.rxdemo.R;
import com.moa.rxdemo.utils.AppUtils;
import com.moa.rxdemo.utils.SystemBarTintManager;
import com.moa.rxdemo.utils.ToastUtils;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2017/12/22 11:32
 */
public abstract class BaseActiivty extends AppCompatActivity implements View.OnClickListener {
    
    protected SystemBarTintManager mTintManager;
    protected Toolbar mToolbar;
    protected View customView;
    protected TextView tvTitle;
    protected ImageView ivBack;
    protected View vDivider;
    
    @Override
    protected void attachBaseContext(Context newBase) {
        // 更新多语言
        Context context = AppUtils.updateConfiguration(newBase);
        super.attachBaseContext(context);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.addActivity(this);
        
        // init status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            initStatusBar();
        }
        
        // 1.init intent
        Bundle bundle = getIntent().getExtras();
        if (savedInstanceState != null) {
            bundle = savedInstanceState;
        }
        if (bundle != null) {
            getSavedData(bundle);
        }
        
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
        }
        
        // init header
        initHeader();
        // init view
        initView();
        // init data
        initData();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.removeActivity(this);
    }
    
    /**
     * Get the catch data from bundle
     *
     * @param bundle
     */
    protected void getSavedData(Bundle bundle) {
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
        View view = findViewById(R.id.toolbar);
        if (view != null) {
            mToolbar = (Toolbar) view;
            setSupportActionBar(mToolbar);
            
            // the default layout
            customView = View.inflate(this, R.layout.tt_action_bar_custom_view, null);
            tvTitle = customView.findViewById(R.id.tv_title);
            ivBack = customView.findViewById(R.id.iv_back);
            vDivider = customView.findViewById(R.id.v_divider);
            
            ivBack.setOnClickListener(this);
            
            setToolbar(customView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT),
                false);
        }
    }
    
    /**
     * 显示返回按钮
     *
     * @param canback
     */
    public void showBackButton(boolean canback) {
        if (ivBack != null) {
            ivBack.setVisibility(canback ? View.VISIBLE : View.GONE);
            vDivider.setVisibility(canback ? View.VISIBLE : View.GONE);
        }
    }
    
    /**
     * 设置标题
     *
     * @param resId
     */
    public void setCustomTitle(int resId) {
        if (tvTitle != null) {
            tvTitle.setText(resId);
        }
    }
    
    /**
     * 设置标题
     *
     * @param title
     */
    public void setCustomTitle(CharSequence title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }
    
    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        this.setCustomTitle(title);
    }
    
    @Override
    public void onClick(View view) {
        if (ivBack != null && view == ivBack) {
            onBackPressed();
        }
    }
    
    /**
     * init layout view
     */
    protected void initView() {
    }
    
    /**
     * Deal data
     */
    protected void initData() {
    }
    
    // state bar
    
    public int getStatusBarHeight() {
        if (mTintManager != null) {
            return mTintManager.getConfig().getStatusBarHeight();
        }
        return 0;
    }
    
    protected void initStatusBar() {
        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setStatusBarTintColor(getStatusBarColor());
    }
    
    /**
     * statebar color
     *
     * @return
     */
    protected int getStatusBarColor() {
        return getResources().getColor(android.R.color.transparent);
    }
    
    
    // Toolbar
    
    protected void setToolbar(View customView, ActionBar.LayoutParams params) {
        setToolbar(customView, params, true);
    }
    
    protected void setToolbar(View customView, ActionBar.LayoutParams customParams, boolean enableBack) {
        if (getSupportActionBar() == null) {
            throw new RuntimeException("Action bar is not set!");
        }
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setCustomView(customView, customParams);
        if (enableBack) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        else {
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            Toolbar parent = (Toolbar) customView.getParent();
            parent.setContentInsetsAbsolute(0, 0);
        }
    }
    
    public void showFragment(Fragment fragment, int containerId, boolean addToBackStack) {
        replaceFragment(fragment, containerId, null, addToBackStack);
    }
    
    public void replaceFragment(Fragment fragment, int containerId, String tag, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
    
    public void addFragment(Fragment fragment, int containerId, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(containerId, fragment, tag);
        transaction.commitAllowingStateLoss();
    }
    
    protected void showToast(String text) {
        ToastUtils.showToast(this, text);
    }
    
}
