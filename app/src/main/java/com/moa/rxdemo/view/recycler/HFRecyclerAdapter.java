package com.moa.rxdemo.view.recycler;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

import java.util.List;

/**
 * 支持显示header和footer的adapter，上拉加载和下拉刷新功能，如不需要这些功能可直接使用{@link RecyclerAdapter}
 *
 * @param <D> adapter中的数据泛型
 */
public abstract class HFRecyclerAdapter<D extends IData> extends RecyclerAdapter<D> {

    /**
     * 在enableHeader为true时设置head才有效
     */
    public static final int ITEM_TYPE_HEADER = -2;
    /**
     * 在enableFooter为true时设置head才有效
     */
    public static final int ITEM_TYPE_FOOTER = -3;

    // 使用系统的刷新组件
    private SwipeRefreshLayout mSwipeRefreshLayout;

    // 加载更多监听器
    private OnLoadMoreListener mOnLoadMoreListener;
    private RecyclerView mRecyclerView;

    // header需要显示的数据
    private Object mHeaderData;
    // 刷新状态
    private int mRefreshStatus = Status.SUCCESS;
    // 当前加载状态
    private int mLoadStatus = Status.SUCCESS;
    // 分页加载，一页数据默认10条
    private int mPageSize = 10;
    // 滚动到底部自动加载
    private boolean mAutoLoadMore;
    // 是否启用header view
    private boolean mEnableHeaderView;
    // 是否启用footer view
    private boolean mEnableFooterView;

    public HFRecyclerAdapter(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        // 去除动画，防止更新footer view的时候闪烁，出现底部分割线
        RecyclerView.ItemAnimator animator = this.mRecyclerView.getItemAnimator();
        if (animator != null) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }

    public void setOnRefreshListener(SwipeRefreshLayout mSwipeRefreshLayout, SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
        if (mSwipeRefreshLayout != null && onRefreshListener != null) {
            mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        }
    }

    /**
     * 设置分页加载page size 默认 {@link #mPageSize}
     *
     * @param mPageSize page size
     */
    public void setPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
    }

    /**
     * 设置是否滚动到底部自动加载
     *
     * @param mAutoLoadMore
     */
    public void setAutoLoadMore(boolean mAutoLoadMore) {
        this.mAutoLoadMore = mAutoLoadMore;
    }

    /**
     * header view是否可用
     *
     * @return
     */
    public boolean isEnableHeaderView() {
        return mEnableHeaderView;
    }

    /**
     * 设置header view是否可用
     *
     * @param mEnableHeaderView
     */
    public void setEnableHeaderView(boolean mEnableHeaderView) {
        this.mEnableHeaderView = mEnableHeaderView;
    }

    /**
     * footer view 是否可用
     *
     * @return
     */
    public boolean isEnableFooterView() {
        return mEnableFooterView;
    }

    /**
     * 设置footer view是否可用
     *
     * @param mEnableFooterView
     */
    public void setEnableFooterView(boolean mEnableFooterView) {
        this.mEnableFooterView = mEnableFooterView;
    }

    /**
     * 设置header要显示的数据，并更新
     *
     * @param hdaderData
     */
    public void setHeaderData(Object hdaderData) {
        this.mHeaderData = hdaderData;
        notifyItemChanged(0);
    }

    @Override
    public int getItemViewType(int position) {
        // 数据为空时
        if (getDataCount() == 0) {
            if (mEnableHeaderView && mEnableEmptyView) {
                if (position == 0) {
                    return ITEM_TYPE_HEADER;
                } else if (position == 1) {
                    return ITEM_TYPE_EMPTY;
                }
            } else if (mEnableEmptyView) {
                if (position == 0) {
                    return ITEM_TYPE_EMPTY;
                }
            } else if (mEnableHeaderView) {
                if (position == 0) {
                    return ITEM_TYPE_HEADER;
                }
            }
        }
        // 数据不为空时
        else {
            // 头部
            if (mEnableHeaderView && position == 0) {
                return ITEM_TYPE_HEADER;
            }
            // 尾部
            else if (mEnableFooterView && position == getItemCount() - 1) {
                return ITEM_TYPE_FOOTER;
            }
        }

        // ITEM_TYPE_NORMAL 默认是0
        return super.getItemViewType(position);

    }

    @Override
    public int getItemCount() {
        if (getDataCount() == 0) {
            if (mEnableHeaderView) {
                return super.getItemCount() + 1;
            } else {
                return super.getItemCount();
            }
        } else {
            if (mEnableHeaderView && mEnableFooterView) {
                return getDataCount() + 2;
            } else if (mEnableHeaderView) {
                return getDataCount() + 1;
            } else if (mEnableFooterView) {
                return getDataCount() + 1;
            } else {
                return super.getItemCount();
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        if (getItemViewType(position) == ITEM_TYPE_FOOTER) {
            holder.bind(mLoadStatus);
            holder.setOnClickListener(v -> {
                loadingMore();
            });
            return;
        } else if (getItemViewType(position) == ITEM_TYPE_HEADER) {
            holder.bind(mHeaderData);
            return;
        } else if(getItemViewType(position) == ITEM_TYPE_EMPTY){
            // 此处过滤掉ITEM_TYPE_EMPTY，防止后面的getRealPosition错位
        } else {
            position = getRealPosition(holder);
        }

        super.onBindViewHolder(holder, position);
    }

    private boolean isRefreshing() {
        return mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing();
    }

    /**
     * 刷新完成，data==null时加载失败，data.size>=0时表示加载完成
     *
     * @param data 刷新加载的数据
     */
    public void refreshFinish(List<D> data) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        if (data != null) {
            // 加载成功没有数据
            if (data.size() == 0) {
                mRefreshStatus = Status.FINISH;
            } else {
                // 加载成功且有数据
                mRefreshStatus = Status.SUCCESS;
            }
        } else {
            // 加载失败
            mRefreshStatus = Status.FAIL;
        }

        // 重置load more的状态
        if (mLoadStatus != Status.SUCCESS) {
            mLoadStatus = Status.SUCCESS;
        }

        // 重置empty view的显示状态
        if (mEnableEmptyView) {
            setEmptyStatus(mRefreshStatus);
        }

        setData(data);
        notifyDataSetChanged();
    }

    /**
     * 加载完成，在data==null时表示加载失败，data.size>=0时表示加载完成
     *
     * @param data
     */
    public void loadFinish(List<D> data) {
        if (data != null) {
            if (data.size() < mPageSize) {
                updateLoadingStatus(Status.FINISH);
            } else {
                updateLoadingStatus(Status.SUCCESS);
            }
            addAll(data);
        } else {
            updateLoadingStatus(Status.FAIL);
        }
    }

    private void updateLoadingStatus(int status) {
        this.mLoadStatus = status;
        // 在加载完成时，设置刷新可用
        if (!isLoading() && mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setEnabled(true);
        }
        notifyItemChanged(getItemCount() - 1);
    }

    public boolean isLoading() {
        return mLoadStatus == Status.LOADING;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 滚动到底部，自动加载
                if (mAutoLoadMore // 自动加载
                        && newState == RecyclerView.SCROLL_STATE_IDLE // 停止滑动
                        && isBottom(mRecyclerView) // 滚动到底部
                        && !isRefreshing()) {// 是否正在刷新
                    loadingMore();
                }
            }
        });
    }

    /**
     * 是否滚动到底部
     *
     * @param recyclerView
     * @return
     */
    private boolean isBottom(RecyclerView recyclerView) {
        // computeVerticalScrollExtent()是当前屏幕显示的区域高度，
        // computeVerticalScrollOffset() 是当前屏幕之前滑过的距离，
        // computeVerticalScrollRange()是整个View控件的高度。

        //RecyclerView.canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
        //RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
        if (recyclerView == null)
            return false;

        return !recyclerView.canScrollVertically(1);

//        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
//            return true;
//
//        return false;
    }

    private void loadingMore() {
        // 正在加载和加载结束不回调事件
        if (mOnLoadMoreListener != null
                && mLoadStatus != Status.LOADING // 正在加载
                && mLoadStatus != Status.FINISH // 加载完成
                && !isRefreshing()) { // 真正刷新
            // 设置为正在加载
            updateLoadingStatus(Status.LOADING);

            // 加载中，禁用刷新
            mSwipeRefreshLayout.setEnabled(false);
            // 回调加载
            mOnLoadMoreListener.onLoadMore();
        }
    }

    /**
     * 获得holder在adapter中的位置，去除header
     *
     * @param holder
     * @return
     */
    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();
        return mEnableHeaderView ? position - 1 : position;
    }

    @Override
    protected boolean isFullItemViewType(int position) {
        return super.isFullItemViewType(position)
                || (getItemViewType(position) == ITEM_TYPE_HEADER)
                || (getItemViewType(position) == ITEM_TYPE_FOOTER);
    }
}
