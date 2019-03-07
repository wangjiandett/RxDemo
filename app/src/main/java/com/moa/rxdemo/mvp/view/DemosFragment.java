package com.moa.rxdemo.mvp.view;

import android.view.View;

import com.moa.baselib.base.ui.BaseFragment;
import com.moa.rxdemo.R;
import com.moa.rxdemo.mvp.view.demons.SampleActivity;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2017/12/22 14:40
 */
public class DemosFragment extends BaseFragment {
    
    @Override
    protected int getLayoutId() {
        return R.layout.tt_fragment_demos;
    }
    
    @Override
    protected void initView(View view) {
        super.initView(view);
        
        findViewById(R.id.btn_demos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SampleActivity.Companion.getIntent(getActivity()));
            }
        });
    }
}
