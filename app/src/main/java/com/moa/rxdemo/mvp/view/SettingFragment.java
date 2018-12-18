package com.moa.rxdemo.mvp.view;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.text.TextUtils;

import com.moa.rxdemo.MyApplication;
import com.moa.rxdemo.R;
import com.moa.rxdemo.base.Config;
import com.moa.rxdemo.base.Constants;
import com.moa.rxdemo.base.dispatcher.TRuntime;
import com.moa.rxdemo.utils.LogUtils;

/**
 * 设置界面
 * <p>
 * Created by：wangjian on 2018/12/17 11:54
 */
public class SettingFragment extends PreferenceFragmentCompat {
    
    private static final String KEY_LANGUAGE_LIST = "language_list";
    
    
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.xml_settings);
        
        Preference languagePreference = findPreference(KEY_LANGUAGE_LIST);
        languagePreference.setOnPreferenceChangeListener(preferenceChangeListener);
    }
    
    Preference.OnPreferenceChangeListener preferenceChangeListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            if (TextUtils.equals(key, KEY_LANGUAGE_LIST)) {
                String value = newValue.toString();
                LogUtils.d("value:" + value);
                updateLanguage(value);
            }
            return true;
        }
    };
    
    private void updateLanguage(String value) {
        String tag = Constants.LAN_AUTO;
        switch (Integer.parseInt(value)) {
            case 0:
                tag = Constants.LAN_AUTO;
                break;
            case 1:
                tag = Constants.LAN_ENGLISH;
                break;
            case 2:
                tag = Constants.LAN_SIMPLE_CHINESE;
                break;
        }
        
        Config.getDefault().setCurrentLocale(getContext(), tag);
        // 此处由于使用Preference，需要等待下面的return true执行完
        // 才能执行杀掉进程，否则Preference状态无法保存完成
        TRuntime.dispatchDelay(new Runnable() {
            @Override
            public void run() {
                // 杀掉进程
                startActivity(SplashActivity.Companion.getIntent(getActivity()));
                MyApplication.finishAllActivity();
            }
        }, 50);
    }
    
}
