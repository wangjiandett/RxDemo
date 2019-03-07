package com.moa.rxdemo.mvp.view.demons

import android.view.View
import com.moa.baselib.base.ui.BaseFragment
import com.moa.baselib.utils.DialogUtils
import com.moa.baselib.utils.LogUtils
import com.moa.baselib.view.SwitchButton
import com.moa.rxdemo.R

/**
 * dispatcher 使用demo
 *
 * Created by：wangjian on 2018/12/21 15:58
 */
class ViewsFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.tt_fragment_views;
    }

    override fun initView(view: View) {
        super.initView(view)
        view.let {


            // 测试没有回调的 switchButton
            val switchButton = view.findViewById(R.id.btn_switch) as SwitchButton

            switchButton.setOnCheckedChangeListener { btn, isCheck ->
                LogUtils.d("isCheck：$isCheck");
            }

            view.findViewById<View>(R.id.btn_toogle).setOnClickListener{
                switchButton.toggleNoEvent()
            }

            view.findViewById<View>(R.id.btn_show_dialog).setOnClickListener{
               DialogUtils.showCenterDialog(context, R.layout.tt_dialog_view)
            }
        }
    }

}