package com.moa.rxdemo.mvp.view.demons.recycler

import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.*
import android.view.View
import android.widget.TextView
import com.moa.rxdemo.R
import com.moa.rxdemo.base.ui.BaseFragment
import com.moa.rxdemo.view.GridDividerItemDecoration
import com.moa.rxdemo.utils.AppUtils
import com.moa.rxdemo.utils.Randoms
import com.moa.rxdemo.view.LinearDividerItemDecoration

/**
 * 类或文件描述
 *
 * Created by：wangjian on 2019/1/9 14:14
 */

class RecyclerViewFragment : BaseFragment() {

    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: MyRecyclerAdapter

    override fun getLayoutId(): Int {
        return R.layout.tt_fragment_recycler
    }

    override fun initView(view: View) {

        recyclerView = view.findViewById(R.id.recycler_view)


        view.findViewById<View>(R.id.btn_linear_vertical).setOnClickListener {
            setLinearManager(LinearLayoutManager.VERTICAL)
        }

        view.findViewById<View>(R.id.btn_linear_horizontal).setOnClickListener {
            setLinearManager(LinearLayoutManager.HORIZONTAL)
        }

        view.findViewById<View>(R.id.btn_grid_horizontal).setOnClickListener {
            setGridManager(GridLayoutManager.HORIZONTAL)
        }

        view.findViewById<View>(R.id.btn_grid_vertical).setOnClickListener {
            setGridManager(GridLayoutManager.VERTICAL)
        }

        view.findViewById<View>(R.id.btn_staggered_horizontal).setOnClickListener {
            setStaggeredManager(StaggeredGridLayoutManager.HORIZONTAL)
        }

        view.findViewById<View>(R.id.btn_staggered_vertical).setOnClickListener {
            setStaggeredManager(StaggeredGridLayoutManager.VERTICAL)
        }

//        var text = TextView(activity)
//        text.text = "headerview"
//        adapter.headerView = text
//
//        text = TextView(activity)
//        text.text = "footerview"
//        adapter.footerView = text
    }

    val list = arrayListOf<String>()

    override fun initData() {

        adapter = MyRecyclerAdapter()

        for (index in 1..20) {
            val random = Randoms.randomInt()
            list.add("这是第$index $random")
        }

        adapter.dataList = list

        adapter.setOnItemClickListener { view, position, data ->
            showToast("item click $data, position:$position")
        }

        adapter.setOnItemLongClickListener { view, position, data ->
            showToast("item long click $data, position:$position")
        }

        setGridManager(LinearLayoutManager.HORIZONTAL)
    }

    private fun setLinearManager(orientation: Int) {

        val layoutManager = LinearLayoutManager(context, orientation, false)
        // 系统默认分割线样式,不支持网状
        val divider = LinearDividerItemDecoration(context, orientation)
        // 自定义分割线样式
        // 同时也可以在apptheme中自定义样式颜色
        // <item name="android:listDivider">@drawable/bg_recyclerview_divider</item>
        divider.setDrawable(AppUtils.getDrawable(context, R.drawable.tt_recycler_divider))

        updateDataView(divider, layoutManager)
    }

    private fun setGridManager(orientation: Int) {

        val layoutManager = GridLayoutManager(context, 3, orientation, false)
        // 网状分割线
        val divider = GridDividerItemDecoration(activity)
        // 自定义分割线样式
        divider.setDrawable(AppUtils.getDrawable(context, R.drawable.tt_recycler_divider))

        updateDataView(divider, layoutManager)
    }

    private fun setStaggeredManager(orientation: Int) {

        // 瀑布流由于item有时不是按照顺序排列，导致分割线绘制会有问题
        val layoutManager = StaggeredGridLayoutManager(3, orientation)
        // 网状分割线
        val divider = GridDividerItemDecoration(activity)
        // 自定义分割线样式
        divider.setDrawable(AppUtils.getDrawable(context, R.drawable.tt_recycler_divider))
        updateDataView(divider, layoutManager)
    }

    private fun updateDataView(divider: RecyclerView.ItemDecoration, layoutManager: RecyclerView.LayoutManager) {
        if (recyclerView.itemDecorationCount > 0) {
            recyclerView.removeItemDecorationAt(0)
        }
        recyclerView.addItemDecoration(divider)

        recyclerView.layoutManager = layoutManager

        //=============注意：每次切换布局管理器的时候都要重新设置一下adapter否则界面会发生错乱===============
        recyclerView.adapter = adapter

        adapter.notifyDataSetChanged()
    }

}