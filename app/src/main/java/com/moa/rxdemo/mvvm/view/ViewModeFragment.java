package com.moa.rxdemo.mvvm.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.moa.baselib.base.ui.BaseListFragment;
import com.moa.baselib.base.ui.adapter.ViewHolder;
import com.moa.baselib.utils.LogUtils;
import com.moa.baselib.utils.ToastUtils;
import com.moa.rxdemo.R;
import com.moa.rxdemo.mvp.bean.SwipeItem;
import com.moa.rxdemo.mvvm.base.LoadState;
import com.moa.rxdemo.mvvm.base.ResponseData;
import com.moa.rxdemo.mvvm.viewmodel.SwipeViewModel;

import java.util.List;

/**
 * mvvm方式调用接口,实现数据回调
 * <p>
 * Created by：wangjian on 2017/12/22 14:40
 */
public class ViewModeFragment extends BaseListFragment<SwipeItem> {
    
    private ListView listView;
    
    @Override
    protected int getLayoutId() {
        return R.layout.tt_fragment_view_model;
    }
    
    @Override
    protected void initView(View view) {
        super.initView(view);
        listView = findViewById(R.id.lv_list);
        bindAdapter(listView);
        
    }
    
    @Override
    protected void initData() {
        // mvvm 加载方式
        final SwipeViewModel swipeViewModel = ViewModelProviders.of(this).get(SwipeViewModel.class);
        
        // 1。监听数据变化
        swipeViewModel.getResponse().observe(this, new Observer<ResponseData<List<SwipeItem>>>() {
            @Override
            public void onChanged(@Nullable ResponseData<List<SwipeItem>> swipeItems) {
                LogUtils.d("request:" + swipeItems.request);

                // 加载状态回调
                // 可在此处控制加载框的显示隐藏
                switch (swipeItems.loadStatus.status) {
                    case LoadState.LOADED:
                        // 隐藏载框  加载完成
                        LogUtils.d("mvvm LOADED:");
                        break;
                    case LoadState.LOADING:
                        // 显示加载框  加载中...
                        LogUtils.d("mvvm LOADING:");
                        break;
                    case LoadState.LOADING_FAIL:
                        // 加载失败
                        ToastUtils.showToast(getActivity(), swipeItems.loadStatus.tipMsg);
                        LogUtils.d("mvvm LOADING_FAIL:");
                        break;
                    case LoadState.LOADING_SUCCESS:
                        // 加载成功
                        LogUtils.d("mvvm LOADING_SUCCESS:");
                        mHolderAdapter.setListAndNotify(swipeItems.response);
                        break;
                }
            }
        });
        
        // 2。加载数据
        swipeViewModel.sendRequest(null);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                swipeViewModel.sendRequest(position + 1);
            }
        });
    }
    
    @Override
    protected ViewHolder<SwipeItem> getViewHolder() {
        return new MyHolder();
    }
    
    class MyHolder extends ViewHolder<SwipeItem> {
        
        private TextView tvText;
        
        @Override
        public View init(SwipeItem data, ViewGroup viewGroup, Context context) {
            View view = View.inflate(context, android.R.layout.simple_list_item_1, null);
            tvText = (TextView) view;
            return view;
        }
        
        @Override
        public void bind(SwipeItem data, int position, Context context) {
            tvText.setText(data.publishedAt);
        }
    }
}
