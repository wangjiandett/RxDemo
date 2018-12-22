package com.moa.rxdemo.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.moa.rxdemo.R;
import com.moa.rxdemo.base.ui.BaseActiivty;
import com.moa.rxdemo.utils.ToastUtils;

import java.util.HashMap;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2017/12/20 16:25
 */
public class MainActivity extends BaseActiivty {
    
    public static final String FRAG_TAG_HOME = "fragment.home";
    public static final String FRAG_TAG_BOARD = "fragment.board";
    public static final String FRAG_TAG_NOTICE = "fragment.notice";
    public static final String FRAG_TAG_SETTING = "fragment.setting";
    
    private BottomNavigationView bottomNavigationView;
    private Fragment mCurrentFragment;
    
    @Override
    protected int getLayoutId() {
        return R.layout.tt_activity_main;
    }
    
    @Override
    protected void initHeader() {
        super.initHeader();
        
        showBack(false);
    }
    
    @Override
    protected void initView() {
        
        mCurrentFragment = new HomeFragment();
        replaceFragment(mCurrentFragment, R.id.fragment_container, FRAG_TAG_HOME, false);
        
        bottomNavigationView = findViewById(R.id.navigation);
        // 清除底部图标变色
        // bottomNavigationView.setItemIconTintList(null);
        // 设置底部文字颜色
        // 这是相同的字体大小可清除缩放动画
        bottomNavigationView.setItemTextAppearanceActive(R.style.bottom_selected_text);
        bottomNavigationView.setItemTextAppearanceInactive(R.style.bottom_normal_text);
        
        // 控制当tab大于3个时不完全显示lable
        // 早期版本需要自行设置参考https://blog.csdn.net/qq_19973845/article/details/82151204
        // app:labelVisibilityMode="labeled"
        // bottomNavigationView.setLabelVisibilityMode(1);
        bottomNavigationView.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            switchFragment(FRAG_TAG_HOME);
                            return true;
                        case R.id.navigation_dashboard:
                            switchFragment(FRAG_TAG_BOARD);
                            return true;
                        case R.id.navigation_demos:
                            switchFragment(FRAG_TAG_NOTICE);
                            return true;
                        case R.id.navigation_settings:
                            switchFragment(FRAG_TAG_SETTING);
                            return true;
                        default:
                            return false;
                    }
                }
            });
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tt_menu_ok, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.navigation_home) {
            ToastUtils.showToast(this, "click menu");
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onBackPressed() {
        // 此处由于DemosFragment中使用了navigation
        // 特殊处理了DemosFragment中的返回键
        if (mCurrentFragment instanceof DemosFragment) {
            boolean back = ((DemosFragment) mCurrentFragment).backUp();
            if (back) {
                return;
            }
        }
        super.onBackPressed();
    }
    
    
    /**
     * 用作缓存每一个tab上的badge方式重复创建，导致重复添加
     */
    HashMap<Integer, Badge> badgeHashMap = new HashMap<>();
    
    /**
     * BottomNavigationView显示角标
     *
     * @param viewIndex  tab索引
     * @param showNumber 显示的数字，小于等于0是将不显示
     */
    public void showBadgeView(int viewIndex, int showNumber) {
        
        if (badgeHashMap.get(viewIndex) != null) {
            badgeHashMap.get(viewIndex).setBadgeNumber(showNumber);
            return;
        }
        
        // 具体child的查找和view的嵌套结构请在源码中查看
        // 从bottomNavigationView中获得BottomNavigationMenuView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        // 从BottomNavigationMenuView中获得childview, BottomNavigationItemView
        if (viewIndex < menuView.getChildCount()) {
            // 获得viewIndex对应子tab
            View view = menuView.getChildAt(viewIndex);
            // 从子tab中获得其中显示图片的ImageView
            View icon = view.findViewById(android.support.design.R.id.icon);
            // 获得图标的宽度
            int iconWidth = icon.getWidth();
            // 获得tab的宽度/2
            int tabWidth = view.getWidth() / 2;
            // 计算badge要距离右边的距离
            int spaceWidth = tabWidth - iconWidth;
            
            // 显示badegeview
            Badge badge = new QBadgeView(this).bindTarget(view).setGravityOffset(spaceWidth, 3, false).setBadgeNumber(
                showNumber);
            badgeHashMap.put(viewIndex, badge);
        }
    }
    
    /**
     * 处理fragment中切换
     *
     * @param tag
     */
    private void switchFragment(String tag) {
        FragmentManager manager = getSupportFragmentManager();
        
        Fragment fragment = manager.findFragmentByTag(tag);
        if (fragment == null) {
            if (FRAG_TAG_HOME.equals(tag)) {
                fragment = new HomeFragment();
            }
            else if (FRAG_TAG_BOARD.equals(tag)) {
                fragment = new BoardFragment();
            }
            else if (FRAG_TAG_NOTICE.equals(tag)) {
                fragment = new DemosFragment();
            }
            else if (FRAG_TAG_SETTING.equals(tag)) {
                fragment = new SettingFragment();
            }
        }
        FragmentTransaction ft = manager.beginTransaction();
        if (fragment != null && fragment.isAdded()) {
            ft.hide(mCurrentFragment).show(fragment).commitAllowingStateLoss();
        }
        else {
            ft.hide(mCurrentFragment).add(R.id.fragment_container, fragment, tag).commitAllowingStateLoss();
        }
        mCurrentFragment = fragment;
    }
    
    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}
