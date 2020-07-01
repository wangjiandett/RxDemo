package com.moa.rxdemo.mvvm.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.moa.baselib.base.net.mvvm.SimpleCallback;
import com.moa.baselib.base.ui.BaseListFragment;
import com.moa.baselib.base.ui.adapter.ViewHolder;
import com.moa.baselib.utils.GsonHelper;
import com.moa.baselib.utils.LogUtils;
import com.moa.rxdemo.R;
import com.moa.rxdemo.mvp.bean.ForecastBean;
import com.moa.rxdemo.mvvm.viewmodel.SwipeModel;

/**
 * mvvm方式调用接口,实现数据回调
 * <p>
 * Created by：wangjian on 2017/12/22 14:40
 */
public class ViewModeFragment extends BaseListFragment<ForecastBean.Forecast> {
    
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
        final SwipeModel swipeViewModel = ViewModelProviders.of(this).get(SwipeModel.class);
        
        // 1。监听数据变化
        swipeViewModel.getSwipeList(101220101).observe(this, new SimpleCallback<ForecastBean.Data>(this){
            @Override
            public void onSuccess(Object request, ForecastBean.Data data) {
                LogUtils.d(GsonHelper.toJson(data));
                mHolderAdapter.setListAndNotify(data.forecast);
            }
        });
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                swipeViewModel.getSwipeList(101220101);
            }
        });
    }
    
    @Override
    protected ViewHolder<ForecastBean.Forecast> getViewHolder() {
        return new MyHolder();
    }
    
    static class MyHolder extends ViewHolder<ForecastBean.Forecast> {
        
        private TextView tvText;

        @Override
        public int getLayoutId() {
            return android.R.layout.simple_list_item_1;
        }

        @Override
        public void initView(View itemView, ForecastBean.Forecast data, Context context) {
            tvText = findView(android.R.id.text1);
        }

        @Override
        public void bindData(ForecastBean.Forecast data, int position, Context context) {
            tvText.setText(data.week);
        }
    }
}
