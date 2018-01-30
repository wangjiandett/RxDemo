package com.moa.rxdemo.mvp.view;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.moa.rxdemo.R;
import com.moa.rxdemo.base.ui.BaseFragment;
import com.moa.rxdemo.base.ui.adapter.HolderAdapter;
import com.moa.rxdemo.base.ui.adapter.ViewHolder;
import com.moa.rxdemo.mvp.bean.Weather;
import com.moa.rxdemo.mvp.contract.WeatherContract;
import com.moa.rxdemo.mvp.model.WeatherModelImpl;
import com.moa.rxdemo.mvp.presenter.WeatherPresenter;
import com.moa.rxdemo.utils.LogUtils;
import com.moa.rxdemo.utils.ToastUtils;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2017/12/22 14:40
 */
public class HomeFragment extends BaseFragment implements WeatherContract.IWeatherView {
    
    private ListView listView;
    private MyAdapter adapter;
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }
    
    @Override
    protected void initView(View view) {
        super.initView(view);
        listView = findViewById(R.id.lv_list);
        adapter = new MyAdapter(getActivity());
        listView.setAdapter(adapter);
    }
    
    @Override
    protected void initData() {
        WeatherPresenter presenter = new WeatherPresenter(this, new WeatherModelImpl());
        presenter.getWeatherList("101010100");
    }
    
    @Override
    public void onSuccess(Weather weather) {
        LogUtils.d("city:" + weather.city);
        adapter.setList(weather.forecast);
        adapter.notifyDataSetChanged();
    }
    
    @Override
    public void onFail(String msg) {
        ToastUtils.show(msg);
        LogUtils.e(msg);
    }
    
    class MyAdapter extends HolderAdapter<Weather.ForecastBean> {
        
        protected MyAdapter(Context context) {
            super(context);
        }
        
        @Override
        protected ViewHolder<Weather.ForecastBean> createHolder(int position, Weather.ForecastBean obj) {
            return new MyHolder();
        }
    }
    
    class MyHolder extends ViewHolder<Weather.ForecastBean> {
        
        private TextView tvDate;
        private TextView tvType;
        private TextView tvTemp;
        private TextView tvWind;
        
        @Override
        public View init(Weather.ForecastBean data, ViewGroup viewGroup, Context context) {
            View view = View.inflate(context, R.layout.weather_list_item, null);
            
            tvDate = view.findViewById(R.id.tv_date);
            tvType = view.findViewById(R.id.tv_type);
            tvTemp = view.findViewById(R.id.tv_temp);
            tvWind = view.findViewById(R.id.tv_wind);
            return view;
        }
        
        @Override
        public void bind(Weather.ForecastBean data, int position, Context context) {
            tvDate.setText(data.date);
            tvType.setText(data.type);
            tvTemp.setText(data.low + "--" + data.high);
            Spanned spanned = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                spanned = Html.fromHtml(data.fengli, Html.FROM_HTML_MODE_LEGACY);
            }
            else {
                spanned = Html.fromHtml(data.fengli);
            }
            
            tvWind.setText(data.fengxiang + "--" + spanned);
        }
    }
}
