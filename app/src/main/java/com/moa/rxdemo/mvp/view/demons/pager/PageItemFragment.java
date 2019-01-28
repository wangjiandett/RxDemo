package com.moa.rxdemo.mvp.view.demons.pager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.moa.rxdemo.R;
import com.moa.rxdemo.base.ui.BaseFragment;

public class PageItemFragment extends BaseFragment {

    private static final String EXTRA_DATA = "extra_data";

    public static PageItemFragment create(String item){
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_DATA, item);
        PageItemFragment fragment = new PageItemFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tt_fragment_page_item;
    }

    @Override
    protected void initView(@NonNull View view) {
        super.initView(view);

        Bundle bundle = getArguments();
        if(bundle != null){
            String index = bundle.getString(EXTRA_DATA);
            int [] colors = new int[]{Color.LTGRAY, Color.DKGRAY, Color.GRAY, Color.YELLOW, Color.CYAN};
            view.setBackgroundColor(colors[Integer.parseInt(index)]);

            TextView textView = view.findViewById(R.id.tv_text);
            textView.setText("当前item是："+index);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("click index: "+index);
                }
            });
        }
    }

}
