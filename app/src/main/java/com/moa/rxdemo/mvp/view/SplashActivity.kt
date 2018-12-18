package com.moa.rxdemo.mvp.view

import android.content.Context
import android.content.Intent
import android.os.Handler
import com.moa.rxdemo.R
import com.moa.rxdemo.base.ui.BaseActiivty

/**
 * 类或文件描述
 *
 * Created by：wangjian on 2018/12/17 17:11
 */
class SplashActivity : BaseActiivty() {

    companion object {

        fun getIntent(context: Context): Intent {
            return Intent(context, SplashActivity::class.java);
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.tt_activity_splash;
    }

    override fun initData() {
        super.initData()

        Handler().postDelayed({
            startActivity(MainActivity.getIntent(this))
            finish()
        }, 10);
    }

}