package com.moa.rxdemo.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.didi.virtualapk.PluginManager;
import com.moa.baselib.RoutePath;
import com.moa.baselib.base.ui.BaseFragment;
import com.moa.plugin_messenger.Messenger;
import com.moa.plugin_messenger.bean.User;
import com.moa.rxdemo.R;

import java.io.File;

/**
 * 类或文件描述
 * <p>
 * Created by：wangjian on 2017/12/22 14:40
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";

    @Override
    protected int getLayoutId() {
        return R.layout.tt_fragment_home;
    }
    
    @Override
    protected void initView(View view) {
        super.initView(view);
        View btn = view.findViewById(R.id.btn_module_a);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePath.MODULE_A_ENTER_ACTIVITY).navigation();
            }
        });

        btn = view.findViewById(R.id.btn_module_b);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePath.MODULE_B_ENTER_ACTIVITY).navigation();
            }
        });

        btn = view.findViewById(R.id.btn_module_c);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePath.MODULE_C_ENTER_ACTIVITY).navigation();
            }
        });

        btn = view.findViewById(R.id.btn_didi_plugin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpPlugin();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        // 获取插件设置到数据
        User user = Messenger.getUser();
        if(user != null){
            showToast(user.getName());
            // 处理完后清空数据
            Messenger.init(null);
        }
    }

    /**
     * 跳转到插件页面
     */
    private void jumpPlugin(){
        final String pkg = "com.moa.plugindemo";
        if (PluginManager.getInstance(getActivity()).getLoadedPlugin(pkg) == null) {
            showToast("plugin [com.moa.plugindemo] not loaded");
            return;
        }

        User user = new User();
        user.setName("我是小白");
        Messenger.init(user);

        // test Activity and Service
        Intent intent = new Intent();
        intent.setClassName(getActivity(), "com.moa.plugindemo.PluginMainActivity");
        startActivity(intent);
    }

    @Override
    protected void initData() {
      //  loadPlugin(getActivity());
    }

    /**
     * 加载插件
     *
     * @param base
     */
    private void loadPlugin(Context base) {
        String apkName = "plugindemo.apk";

        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Toast.makeText(getActivity(), "sdcard was NOT MOUNTED!", Toast.LENGTH_SHORT).show();
        }
        PluginManager pluginManager = PluginManager.getInstance(base);
        File apk = new File(Environment.getExternalStorageDirectory(), apkName);
        if (apk.exists()) {
            try {
                pluginManager.loadPlugin(apk);
                showToast("load success");
                Log.i(TAG, "Loaded plugin from apk: " + apk);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                File file = new File(base.getFilesDir(), apkName);
                java.io.InputStream inputStream = base.getAssets().open(apkName, 2);
                java.io.FileOutputStream outputStream = new java.io.FileOutputStream(file);
                byte[] buf = new byte[1024];
                int len;

                while ((len = inputStream.read(buf)) > 0) {
                    outputStream.write(buf, 0, len);
                }

                outputStream.close();
                inputStream.close();
                pluginManager.loadPlugin(file);
                showToast("plugin load success");
                Log.i(TAG, "Loaded plugin from assets: " + file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        try {

            // 加载显示插件中的fragment
            Class<Fragment> fragmentClass = (Class<Fragment>) Class.forName("com.moa.plugindemo.PluginFragment");
            Fragment fragment = fragmentClass.newInstance();
            getChildFragmentManager().beginTransaction().add(R.id.fl_container, fragment, null).commit();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
    }
    
}
