package com.moa.rxdemo.mvp.view.demons.recycler

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.moa.baselib.base.ui.BaseFragment
import com.moa.baselib.utils.AppUtils
import com.moa.baselib.utils.Randoms
import com.moa.baselib.view.recycler.GridDividerItemDecoration
import com.moa.baselib.view.recycler.LinearDividerItemDecoration
import com.moa.rxdemo.R

/**
 * 类或文件描述
 *
 * Created by：wangjian on 2019/1/9 14:14
 */

open class RecyclerViewManagerFragment : BaseFragment() {

    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: ManagerAdapter

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
    }

    val list = arrayListOf<Data>()

    override fun initData() {

        adapter = ManagerAdapter()
        adapter.isEnableEmptyView = true

        for (index in 1..20) {
            val random = Randoms.randomInt()
            list.add(Data("这是第$index $random"))
        }

        adapter.data = list

        adapter.setOnItemClickListener { view, position, data ->
            showToast("item click $data, position:$position")
        }

        adapter.setOnItemLongClickListener { view, position, data ->
            showToast("item long click $data, position:$position")
        }

        setGridManager(LinearLayoutManager.HORIZONTAL)
    }

    public fun setLinearManager(orientation: Int) {

        val layoutManager = LinearLayoutManager(context, orientation, false)
        // 系统默认分割线样式,不支持网状
        val divider = LinearDividerItemDecoration(context, orientation)
        // 自定义分割线样式
        // 同时也可以在apptheme中自定义样式颜色
        // <item name="android:listDivider">@drawable/bg_recyclerview_divider</item>
        divider.setDrawable(AppUtils.getDrawable(context, R.drawable.tt_recycler_divider))

        updateDataView(divider, layoutManager)
    }

    public fun setGridManager(orientation: Int) {

        val layoutManager = GridLayoutManager(context, 3, orientation, false)
        // 网状分割线
        val divider = GridDividerItemDecoration(activity)
        divider.setEnableEmptyView(true)
        // 自定义分割线样式
        divider.setDrawable(AppUtils.getDrawable(context, R.drawable.tt_recycler_divider))

        updateDataView(divider, layoutManager)
    }

    public fun setStaggeredManager(orientation: Int) {

        // 瀑布流由于item有时不是按照顺序排列，导致分割线绘制会有问题
        val layoutManager = StaggeredGridLayoutManager(2, orientation)
        // 网状分割线
        val divider = GridDividerItemDecoration(activity)
        divider.setEnableEmptyView(true)
        // 自定义分割线样式
        divider.setDrawable(AppUtils.getDrawable(context, R.drawable.tt_recycler_divider))
        updateDataView(divider, layoutManager)
    }

    private fun updateDataView(divider: RecyclerView.ItemDecoration, layoutManager: RecyclerView.LayoutManager) {
        // 移除已有的divider防止重复添加
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