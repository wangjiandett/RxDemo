package com.moa.rxdemo.mvp.view;

import android.content.Context;
import android.content.Intent;

import com.moa.baselib.base.dispatcher.Runtimes;
import com.moa.baselib.base.ui.BaseActivity;
import com.moa.rxdemo.R;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2018/12/20 16:04
 */
public class SplashActivity extends BaseActivity {
    
    @Override
    protected int getLayoutId() {
        return R.layout.tt_activity_splash;
    }
    
    @Override
    protected void initData() {
        super.initData();
        Runtimes.dispatchDelay(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.getIntent(SplashActivity.this));
                finish();
            }
        },20);
    }
    
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, SplashActivity.class);
        return intent;
    }
}
