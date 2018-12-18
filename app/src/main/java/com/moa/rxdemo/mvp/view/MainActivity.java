package com.moa.rxdemo.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.moa.rxdemo.R;
import com.moa.rxdemo.base.ui.BaseActiivty;
import com.moa.rxdemo.utils.ToastUtils;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2017/12/20 16:25
 */
public class MainActivity extends BaseActiivty  {
    
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
                        case R.id.navigation_notifications:
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
        if(item.getItemId() == R.id.navigation_home){
            ToastUtils.showToast(this, "click menu");
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
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
                fragment = new NoticeFragment();
            }
            else if(FRAG_TAG_SETTING.equals(tag)){
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
    
    public static Intent getIntent(Context context){
        return new Intent(context, MainActivity.class);
    }
}
