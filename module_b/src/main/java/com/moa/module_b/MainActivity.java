package com.moa.module_b;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.moa.baselib.RoutePath;
import com.moa.baselib.base.ui.BaseActivity;

@Route(path = RoutePath.MODULE_B_ENTER_ACTIVITY)
public class MainActivity extends BaseActivity {

    @Override
    protected void initHeader() {
        super.initHeader();
        //showBackButton(false);
        setTitle(R.string.b_app_name);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.b_activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        findViewById(R.id.btn_2_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePath.MODULE_APP_ENTER_ACTIVITY).navigation();
            }
        });

        findViewById(R.id.btn_2_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePath.MODULE_A_ENTER_ACTIVITY).navigation();
            }
        });

        findViewById(R.id.btn_2_c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePath.MODULE_C_ENTER_ACTIVITY).navigation();
            }
        });
    }
}
