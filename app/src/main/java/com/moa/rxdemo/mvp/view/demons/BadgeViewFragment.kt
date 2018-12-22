package com.moa.rxdemo.mvp.view.demons

import android.view.View
import com.moa.rxdemo.R
import com.moa.rxdemo.base.ui.BaseFragment
import com.moa.rxdemo.mvp.view.MainActivity

/**
 * dispatcher 使用demo
 *
 * Created by：wangjian on 2018/12/21 15:58
 */
class BadgeViewFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.tt_fragment_badgeview;
    }

    override fun initView(view: View?) {
        super.initView(view)
        view?.let {
            it.findViewById<View>(R.id.btn_show_badge).setOnClickListener({
                showOrHideBadgeView(true)
            })

            it.findViewById<View>(R.id.btn_hide_badge).setOnClickListener({
                showOrHideBadgeView(false)
            })

        }
    }

    fun showOrHideBadgeView(show: Boolean){
        if(activity is MainActivity){
            (activity as MainActivity).showBadgeView(2, (if(show) 22 else 0))
        }
    }
}