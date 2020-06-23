package com.moa.rxdemo.mvp.view.demons.pager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.moa.baselib.base.ui.BaseFragment;
import com.moa.baselib.utils.DisplayUtils;
import com.moa.baselib.view.pager.ScrollerViewPager;
import com.moa.rxdemo.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * 无限滚动的view pager实现广告，此处执行修改布局展示样式即可，其他逻辑已经实现
 * <p>
 * 目前，无限循环的viewpager有两种实现方式：
 * 1. 使adapter的getCount()返回Integer.MAX_VALUE，再在初始化时设置当前页面为第几百几千页(如：ViewPager.setCurrentItem(100*data.size));
 * 2. 通过监听viewpager的滑动来设置页面。如当前有数据123，则设置页面为31231，当页面滑动到第一个3时，设置当前页面为第二个3，那么左右都可以滑动，当其滑动到第二个1时同理。
 *
 * <a href="https://blog.csdn.net/anyfive/article/details/52525262">具体实现和原理参考</a>
 *
 * @author wangjian
 */
public class PagersFragment extends BaseFragment {

    public static final String EXTRA = "extra";
    public static final String EXTRA_LAYOUT_ID = "extra_layout_id";
    public static final String EXTRA_INTERVAL = "interval";

    /**
     * PagersFragment
     *
     * @param data     数据源
     * @param interval 滚动间隔
     * @param layoutId viewPager布局（布局中必须包括ViewPager的id为view_pager和RadioGroup的id为radio_group）
     */
    public static PagersFragment create(ArrayList<String> data, int interval, int layoutId) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(EXTRA, data);
        bundle.putInt(EXTRA_INTERVAL, interval);
        bundle.putInt(EXTRA_LAYOUT_ID, layoutId);
        PagersFragment fragment = new PagersFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    /**
     * 显示滚动广告
     *
     * @param manager     manager
     * @param data        数据源
     * @param interval    滚动间隔
     * @param containerId 显示的容器的view id
     * @param layoutId    viewPager布局（布局中必须包括ViewPager的id为view_pager和RadioGroup的id为radio_group）
     */
    public static void showPagerFragment(FragmentManager manager, ArrayList<String> data, int interval, int containerId, int layoutId) {
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = create(data, interval, layoutId);
        transaction.replace(containerId, fragment, null);
        transaction.commit();
    }

    /**
     * 显示滚动广告
     *
     * @param manager     manager
     * @param data        数据源
     * @param containerId 显示的容器的view id
     */
    public static void showPagerFragment(FragmentManager manager, ArrayList<String> data, int containerId) {
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = create(data, INTERVAL_TIME, LAYOUT_ID);
        transaction.replace(containerId, fragment, null);
        transaction.commit();
    }

    private ScrollerViewPager viewPager;
    private RadioGroup radioGroup;
    private PagesAdapter adapter;
    private MyHandler myHandler;

    // 默认布局样式
    private static final int LAYOUT_ID = R.layout.tt_fragment_pagers;
    // 默认滚动间隔
    private static final int INTERVAL_TIME = 3000;
    // 布局layout
    private int layoutId = LAYOUT_ID;
    // 滚动时间间隔
    private int interval = INTERVAL_TIME;
    // 当前显示的界面
    private int currentIndex = 1;
    // 是否停止滚动
    private boolean isStopped = true;


    private ArrayList<String> dataList;

    @Override
    protected int getLayoutId() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            layoutId = bundle.getInt(EXTRA_LAYOUT_ID, LAYOUT_ID);
        }
        return layoutId;
    }

    @Override
    protected void initView(@NonNull View view) {
        super.initView(view);
        myHandler = new MyHandler(this);
        viewPager = view.findViewById(R.id.view_pager);
        radioGroup = view.findViewById(R.id.radio_group);
        adapter = new PagesAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        //设置页面间距
        //viewPager.setPageMargin(ScreenUtils.dp(35));
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // 通过监听viewpager的滑动来设置页面。如当前有数据012，则设置页面为301230，
                // 当页面滑动到第一个3时，设置当前页面为第二个3，那么左右都可以滑动，
                // 同理，当其滑动到第二个0时,设置当前页面为第一0，那么左右都可以滑动。


                // 如：count 6（第一和最后一个是重复数据）
                //position = 0 ,index = 3
                //position = 1 ,index = 0
                //position = 2 ,index = 1
                //position = 3 ,index = 2
                //position = 4 ,index = 3
                //position = 5 ,index = 0

                int index = 0;
                if (position == 0) {
                    index = adapter.getCount() - 3;
                    viewPager.setCurrentItem(adapter.getCount() - 2, false);
                } else if (position == adapter.getCount() - 1) {
                    viewPager.setCurrentItem(1, false);
                } else {
                    index = position - 1;
                }

                radioGroupCheckIndex(index);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                // SCROLL_STATE_IDLE：空闲状态
                // SCROLL_STATE_DRAGGING：正在拖动page状态
                // SCROLL_STATE_SETTLING：手指已离开屏幕，自动完成剩余的动画效果
                // 当正在dragging页面时，停止自动滚动。否则继续滚动
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    pause();
                } else {
                    resume();
                }
            }
        });
    }

    private void pause() {
        if (!isStopped) {
            isStopped = true;
            myHandler.removeCallbacksAndMessages(null);
        }
    }

    private void resume() {
        if (isStopped) {
            isStopped = false;
            myHandler.sendEmptyMessageDelayed(1, interval);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    @Override
    protected void initData() {
        super.initData();

        Bundle bundle = getArguments();
        if (bundle != null) {
            dataList = bundle.getStringArrayList(EXTRA);
            interval = bundle.getInt(EXTRA_INTERVAL, INTERVAL_TIME);

            if (dataList != null && dataList.size() > 0) {
                List<Fragment> fragments = new ArrayList<>();
                // 数据格式为
                // 301230
                // 通过监听viewpager的滑动来设置页面。如当前有数据012，则设置页面为301230，
                // 当页面滑动到第一个3时，设置当前页面为第二个3，那么左右都可以滑动，
                // 同理，当其滑动到第二个0时,设置当前页面为第一0，那么左右都可以滑动。

                // 第一个 放最后一个数据
                fragments.add(PageItemFragment.create(dataList.get(dataList.size() - 1)));

                for (int i = 0; i < dataList.size(); i++) {
                    fragments.add(PageItemFragment.create(dataList.get(i)));
                }

                // 最后一个 放第一个数据
                fragments.add(PageItemFragment.create(dataList.get(0)));

                adapter.setFragments(fragments);
                adapter.notifyDataSetChanged();

                // 设置默认显示的页面为第2个
                viewPager.setCurrentItem(currentIndex);

                initIndicators();

                // 第一次开始循环
                resume();
            }
        }
    }

    /**
     * 循环设置当前页面
     */
    private void interval() {
        currentIndex = viewPager.getCurrentItem();
        currentIndex = currentIndex + 1;

        if (currentIndex == adapter.getCount()) {
            currentIndex = 0;
        }

        viewPager.setCurrentItem(currentIndex);

        // 开启下次的循环
        myHandler.sendEmptyMessageDelayed(1, interval);
    }

    /**
     * 初始化indicator
     */
    private void initIndicators() {
        radioGroup.removeAllViews();
        // 需要去除第一个和最后一个重复的item
        int count = adapter.getCount() - 2;
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(DisplayUtils.dip2px(10), DisplayUtils.dip2px(10));
        params.leftMargin = DisplayUtils.dip2px(3);
        params.rightMargin = DisplayUtils.dip2px(3);

        for (int i = 0; i < count; i++) {
            View view = View.inflate(getActivity(), R.layout.tt_item_radio, null);
            radioGroup.addView(view, params);
        }

        // 默认选中第一个
        radioGroupCheckIndex(0);
    }

    /**
     * 根据index设置item选中状态
     *
     * @param index
     */
    private void radioGroupCheckIndex(int index) {
        if (index < radioGroup.getChildCount()) {
            View view = radioGroup.getChildAt(index);
            if (view instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) view;
                radioButton.setChecked(true);
            }
        }
    }

    private static class MyHandler extends Handler {

        private WeakReference<PagersFragment> weakReference;

        MyHandler(PagersFragment fragment) {
            weakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PagersFragment fragment = weakReference.get();
            if (fragment != null && !fragment.isStopped) {
                fragment.interval();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        myHandler.removeCallbacksAndMessages(null);
        myHandler = null;
    }
}
