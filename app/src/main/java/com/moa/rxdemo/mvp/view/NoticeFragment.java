package com.moa.rxdemo.mvp.view;

import android.view.View;

import com.moa.rxdemo.R;
import com.moa.rxdemo.base.ui.BaseFragment;
import com.moa.rxdemo.base.ui.H5Activity;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2017/12/22 14:40
 */
public class NoticeFragment extends BaseFragment{
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notice;
    }
    
    @Override
    protected void initView(View view) {
        super.initView(view);
        findViewById(R.id.notice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                H5Activity.go(getActivity(), new H5Activity.H5Request("快递查询","https://m.kuaidi100.com/index_all.html"));
            }
        });
    }
}
