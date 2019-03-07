package com.moa.rxdemo.mvp.view.demons

import android.content.Context
import android.content.Intent
import androidx.navigation.Navigation
import com.moa.baselib.base.ui.BaseActivity
import com.moa.rxdemo.R

/**
 * 类或文件描述
 *
 * Created by：wangjian on 2018/12/24 14:23
 */
class SampleActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.tt_activity_samples
    }

    /**
     * 自定义处理fragment返回事件
     */
    override fun onBackPressed() {
        val back = Navigation.findNavController(this, R.id.demos_navigation).navigateUp();
        if(back){
            return
        }
        super.onBackPressed()
    }


    companion object {
        fun getIntent(context: Context): Intent{
            return Intent(context, SampleActivity::class.java);

        }
    }
}