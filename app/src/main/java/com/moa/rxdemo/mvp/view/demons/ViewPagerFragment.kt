package com.moa.rxdemo.mvp.view.demons

import android.view.View
import com.moa.rxdemo.R
import com.moa.rxdemo.base.ui.BaseFragment
import com.moa.rxdemo.mvp.view.demons.pager.PagersFragment

/**
 * viewpager 滚动广告demo
 */
class ViewPagerFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.tt_fragment_view_pager
    }

    override fun initView(view: View) {
        super.initView(view)

        val list = arrayListOf<String>()

        list.add("0")
        list.add("1")
        list.add("2")
        list.add("3")
        list.add("4")

        // 在需要显示 viewpager的地方直接显示
        PagersFragment.showPagerFragment(fragmentManager, list, R.id.fragment_container)

    }

}
