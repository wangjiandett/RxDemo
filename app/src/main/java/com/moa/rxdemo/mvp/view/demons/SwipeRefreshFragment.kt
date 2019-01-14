package com.moa.rxdemo.mvp.view.demons

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import com.moa.rxdemo.R
import com.moa.rxdemo.base.ui.BaseListFragment
import com.moa.rxdemo.base.ui.adapter.ViewHolder
import com.moa.rxdemo.mvp.bean.SwipeItem
import com.moa.rxdemo.mvp.contract.SwipeContract
import com.moa.rxdemo.mvp.model.SwipeModelImpl
import com.moa.rxdemo.mvp.presenter.SwipePresenter
import com.moa.rxdemo.view.swipetoloadlayou.OnLoadMoreListener
import com.moa.rxdemo.view.swipetoloadlayou.OnRefreshListener
import com.moa.rxdemo.view.swipetoloadlayou.SwipeToLoadLayout

/**
 * SwipeRefresh view 使用demo
 *
 * Created by：wangjian on 2018/12/22 13:14
 */
class SwipeRefreshFragment : BaseListFragment<SwipeItem>(), SwipeContract.ISwipeView, OnRefreshListener, OnLoadMoreListener {

    private lateinit var swipeLoadLayout: SwipeToLoadLayout
    private lateinit var gridView: GridView

    private lateinit var presenter: SwipePresenter

    // 分页数据条数
    private val pageSize = 20
    // 当前第几页
    private var currentPage: Int = 1
    // 数据是否加载完成
    private var isDataLoadFinish: Boolean = false

    override fun getLayoutId(): Int {
        return R.layout.tt_fragment_swipe_refresh
    }

    override fun initView(view: View?) {
        super.initView(view)
        presenter = SwipePresenter(this, SwipeModelImpl())

        view?.let {
            swipeLoadLayout = it.findViewById(R.id.swipe_layout)
            // 设置上拉下拉监听器
            swipeLoadLayout.setOnRefreshListener(this)
            swipeLoadLayout.setOnLoadMoreListener(this)

            gridView = it.findViewById(R.id.swipe_target)
            bindAdapter(gridView)
        }
    }

    override fun initData() {
        super.initData()
        loadSwipeList()
    }

    private fun loadSwipeList() {
        presenter.getSwipeList(currentPage)
    }

    /**
     * 刷新操作
     */
    override fun onRefresh() {
        currentPage = 1
        loadSwipeList()
    }

    /**
     * 上拉加载操作
     */
    override fun onLoadMore() {
        if (isDataLoadFinish) {
            swipeLoadLayout.isLoadMoreEnabled = false
        }
        loadSwipeList()
    }

    override fun onSuccess(itemList: MutableList<SwipeItem>?) {
        itemList?.let {
            if (currentPage == 1) {
                swipeLoadLayout.isRefreshing = false
                mHolderAdapter.list = itemList
            } else {
                swipeLoadLayout.isLoadingMore = false
                mHolderAdapter.list.addAll(itemList)
            }

            // 加载条数等于pageSize时，说明还有数据
            // 此时pageSize+1
            if (itemList.size == pageSize) {
                currentPage += 1

            }
            // 加载的条数小于pageSize说明数据加载完了，就不要上拉加载了
            else if (itemList.size < pageSize) {
                isDataLoadFinish = true
            }

            mHolderAdapter.notifyDataSetChanged()
        }

        finishRefresh()
    }

    /**
     * 结束刷新或加载
     */
    private fun finishRefresh() {
        if (swipeLoadLayout.isRefreshing) {
            swipeLoadLayout.isRefreshing = false
        }

        if (swipeLoadLayout.isLoadingMore) {
            swipeLoadLayout.isLoadingMore = false
        }
    }

    override fun onFail(msg: String?) {
        showToast(msg)
        finishRefresh()
    }

    override fun getViewHolder(): ViewHolder<SwipeItem> {
        return SamplesHolder()
    }

    private class SamplesHolder : ViewHolder<SwipeItem>() {

        lateinit var tvText: TextView

        override fun init(data: SwipeItem?, viewGroup: ViewGroup?, context: Context?): View {
            val view = View.inflate(context, android.R.layout.simple_list_item_1, null)
            tvText = view as TextView
            return view
        }

        override fun bind(data: SwipeItem?, position: Int, context: Context?) {
            tvText.text = data!!.createdAt
        }
    }
}