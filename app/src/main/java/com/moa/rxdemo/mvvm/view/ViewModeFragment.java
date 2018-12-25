package com.moa.rxdemo.mvvm.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.moa.rxdemo.R;
import com.moa.rxdemo.base.ui.BaseListFragment;
import com.moa.rxdemo.base.ui.adapter.ViewHolder;
import com.moa.rxdemo.mvp.bean.SwipeItem;
import com.moa.rxdemo.mvvm.base.LoadState;
import com.moa.rxdemo.mvvm.viewmodel.SwipeViewModel;
import com.moa.rxdemo.utils.LogUtils;
import com.moa.rxdemo.utils.ToastUtils;

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
        SwipeViewModel swipeViewModel = ViewModelProviders.of(this).get(SwipeViewModel.class);
        
        // 1。监听数据变化
        swipeViewModel.getLiveData().observe(this, new Observer<List<SwipeItem>>() {
            @Override
            public void onChanged(@Nullable List<SwipeItem> swipeItems) {
                // load success
                mHolderAdapter.setListAndNotify(swipeItems);
            }
        });
    
        // 2。监听加载状态变化
        swipeViewModel.getLoadStatus().observe(this, new Observer<LoadState>() {
            @Override
            public void onChanged(@Nullable LoadState loadState) {
                // 加载状态回调
                // 可在此处控制加载框的显示隐藏
                switch (loadState.status){
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
                        ToastUtils.showToast(getActivity(), loadState.tipMsg);
                        LogUtils.d("mvvm LOADING_FAIL:");
                        break;
                    case LoadState.LOADING_SUCCESS:
                        // 加载成功
                        LogUtils.d("mvvm LOADING_SUCCESS:");
                        break;
                }
            }
        });
    
        // 3。加载数据
        swipeViewModel.loadData(1);
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
