package com.moa.rxdemo.mvp.view.demons.recycler

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.moa.baselib.base.ui.BaseFragment
import com.moa.baselib.utils.AppUtils
import com.moa.baselib.utils.Randoms
import com.moa.baselib.view.recycler.GridDividerItemDecoration
import com.moa.baselib.view.recycler.Status
import com.moa.rxdemo.R

/**
 * 类或文件描述
 *
 * Created by：wangjian on 2019/1/9 14:14
 */

class RefreshRecyclerFragment : BaseFragment() {


    private lateinit var adapter: MyHFRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun getLayoutId(): Int {
        return R.layout.tt_fragment_refresh_recycler
    }

    override fun initView(view: View) {
        super.initView(view)

        swipeRefreshLayout = view.findViewById(R.id.swipe_layout)
        recyclerView = view.findViewById(R.id.recycler_view)
        adapter = MyHFRecyclerAdapter(recyclerView, this)

        // ----------------------------------------------
        val layoutManager = GridLayoutManager(context, 3)
        // 系统默认分割线样式,不支持网状
        //val divider = LinearDividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        // 网状分割线
        val divider = GridDividerItemDecoration(activity)
        // 自定义分割线样式
        // 同时也可以在apptheme中自定义样式颜色
        // <item name="android:listDivider">@drawable/bg_recyclerview_divider</item>
        divider.setDrawable(AppUtils.getDrawable(context, R.drawable.tt_recycler_divider))


        recyclerView.layoutManager = layoutManager as RecyclerView.LayoutManager?
        // 由于adapter中onAttachedToRecyclerView和onViewAttachedToWindow需要获取manager所以要放到
        // 设置manager后面执行，才能获取到
        recyclerView.adapter = adapter

        divider.setHasFooter(true)
        divider.setHasHeader(true)

        recyclerView.addItemDecoration(divider)


        // ----------------------------------------------

        // enable footer
        adapter.isEnableFooterView = true
        // auto load more
        //adapter.setAutoLoadMore(true)

        // enable empty view
        adapter.isEnableEmptyView = true

        // enable header
        adapter.isEnableHeaderView = true
        adapter.setHeaderData("我是哈德")

        // load more Listener
        adapter.setOnLoadMoreListener {
            loadData()
        }

        // refresh Listener
        adapter.setOnRefreshListener(swipeRefreshLayout) {
            refresh()
        }

        adapter.setOnItemClickListener { _, _, _ ->
            adapter.clear()
        }
    }

    override fun initData() {
        refresh()
    }

    fun refresh(){
        adapter.emptyStatus = Status.LOADING

        recyclerView.postDelayed({
            val list = arrayListOf<Data>()
            for (index in 1..30) {
                val random = Randoms.randomInt()
                list.add(Data("点击清空数据，这是第$index"))
            }

            adapter.refreshFinish(list)
        }, 3000)
    }

    private var page = 1

    /**
     * 封装load more加载数据
     */
    private fun loadData() {

        recyclerView.postDelayed({
            val list = arrayListOf<Data>()
            // 默认pageSize = 10，这里也一页加载10条数据
            var range = 10

            // 第6页加载5条数据，表示已经加载完成
            if (page == 6) {
                range = 5
            }

            for (index in 0..range) {
                val random = Randoms.randomInt()
                list.add(Data("这是第${adapter.itemCount + index} $random"))
            }

            if (page == 4) {
                adapter.loadFinish(null)
            } else {
                adapter.loadFinish(list)
            }

            page ++
        }, 2000)
    }
}