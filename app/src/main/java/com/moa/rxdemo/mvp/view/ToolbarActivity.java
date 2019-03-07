package com.moa.rxdemo.mvp.view;

import android.content.Context;
import android.content.Intent;

import com.moa.baselib.base.ui.BaseActivity;
import com.moa.rxdemo.R;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2017/12/20 16:25
 */
public class ToolbarActivity extends BaseActivity {
    
    @Override
    protected int getLayoutId() {
        return R.layout.tt_activity_toobar;
    }
    
    public static void go(Context context){
        Intent intent = new Intent(context, ToolbarActivity.class);
        context.startActivity(intent);
    }
   
}
