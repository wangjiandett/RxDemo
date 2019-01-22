package com.moa.rxdemo.view.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装对RecyclerView adapter,支持empty view显示，item的点击长按事件。如下使用下拉刷新，上拉加载，等更多功能请参看 {@link HFRecyclerAdapter}
 * <p>
 * Created by：wangjian on 2019/1/8 17:29
 */
public abstract class RecyclerAdapter<D extends IData> extends RecyclerView.Adapter<RecyclerHolder> implements
        View.OnClickListener, View.OnLongClickListener {

    /**
     * empty view类型
     */
    public static final int ITEM_TYPE_EMPTY = -1;

    /**
     * empty view显示的状态
     */
    private int mEmptyStatus = Status.FINISH;
    /**
     * empty view是否可用
     */
    protected boolean mEnableEmptyView = false;

    private OnItemClickListener<D> onItemClickListener;
    private OnItemLongClickListener<D> onItemLongClickListener;

    /**
     * item单击监听
     *
     * @param <D> 数据模型
     */
    public interface OnItemClickListener<D> {
        /**
         * item点击回调
         *
         * @param view     点击的view，即holder中的itemView
         * @param position 点击的位置
         * @param data     回调item数据
         */
        void onItemClick(View view, int position, D data);
    }

    /**
     * item长按监听
     *
     * @param <D> 数据模型
     */
    public interface OnItemLongClickListener<D> {
        /**
         * 长按item回调
         *
         * @param view     长按的view，即holder中的itemView
         * @param position 点击的位置
         * @param data     回调item数据
         */
        void onItemLongClick(View view, int position, D data);
    }

    /**
     * 设置item单击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener<D> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置item长按事件
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener<D> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /**
     * empty view是否可用
     *
     * @return true 可用，false 不可以
     */
    public boolean isEnableEmptyView() {
        return mEnableEmptyView;
    }

    /**
     * 设置empty view是否可用
     *
     * @param mEnableEmptyView true 可用，false 不可以
     */
    public void setEnableEmptyView(boolean mEnableEmptyView) {
        this.mEnableEmptyView = mEnableEmptyView;
    }

    /**
     * 获取当前empty view显示状态 {@link Status}
     *
     * @return 当前显示状态
     */
    public int getEmptyStatus() {
        return mEmptyStatus;
    }

    /**
     * 更新empty view状态
     *
     * @param emptyStatus
     */
    public void setEmptyStatus(int emptyStatus) {
        if(this.mEmptyStatus != emptyStatus){
            this.mEmptyStatus = emptyStatus;
            for (int position = 0; position < getItemCount(); position++) {
                if (getItemViewType(position) == ITEM_TYPE_EMPTY) {
                    notifyItemChanged(position);
                    return;
                }
            }
        }
    }


    /**
     * 执行刷新操作
     */
    public void refresh() {
        // 需要自定义
    }

    //----------------------start-------以下对数据集合操作-----------------------------------
    /**
     * 数据源
     */
    private List<D> mDataList;

    public List<D> getData() {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        return mDataList;
    }

    public void setData(List<D> data) {
        if(data == null){
            data = new ArrayList<>();
        }
        this.mDataList = data;
    }

    public void remove(D item) {
        int index = getData().indexOf(item);
        remove(index);
    }

    public void remove(int index) {
        getData().remove(index);
        notifyItemRemoved(index);
    }

    public void add(D item, int index) {
        getData().add(index, item);
        notifyItemChanged(index);
    }

    public void add(D item) {
        getData().add(item);
        int index = getData().indexOf(item);
        notifyItemChanged(index);
    }

    public void addAll(List<D> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        getData().addAll(dataList);
        notifyItemRangeChanged(getData().size() - dataList.size(), getData().size());
    }

    public void addAll(int index, List<D> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        getData().addAll(index, dataList);
        notifyItemRangeChanged(index, index + dataList.size());
    }

    public void clear() {
        getData().clear();
        notifyDataSetChanged();
    }

    //---------------------end--------对数据集合操作-----------------------------------

    /**
     * 加载item需要显示的view
     *
     * @param viewGroup parent view
     * @param resid 布局id
     * @return view
     */
    public View getItemView(ViewGroup viewGroup, int resid) {
        // 此处获取item view需要传入viewGroup，否则item view布局不居中
        return LayoutInflater.from(viewGroup.getContext()).inflate(resid, viewGroup, false);
    }

    public D getItem(int position) {
        if (getData().size() > position && position > -1) {
            return getData().get(position);
        }
        return null;
    }

    /**
     * 获取真实数据长度
     *
     * @return
     */
    public int getDataCount() {
        return getData().size();
    }

    @Override
    public int getItemCount() {
        // count = 0时，引入empty view
        return getDataCount() != 0 ? getDataCount() : (mEnableEmptyView ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        // 数据为空时
        if (mEnableEmptyView && getDataCount() == 0 && position == 0) {
            return ITEM_TYPE_EMPTY;
        } else if (getData().size() > position && position > -1) {
            D d = getData().get(position);
            if (d != null) {
                return d.getType();
            }
        }

        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        if (getItemViewType(position) == ITEM_TYPE_EMPTY) {
            holder.bind(mEmptyStatus);
        } else {
            onBindViewHolder(getItem(position), position, holder);
            bindListener(holder);
        }
    }

    protected void onBindViewHolder(D data, int position, RecyclerHolder<D> holder) {
        holder.bind(data);
    }

    protected void bindListener(RecyclerHolder holder) {
        if (onItemClickListener != null) {
            holder.setOnClickListener(this);
        }

        if (onItemLongClickListener != null) {
            holder.setOnLongClickListener(this);
        }
    }

    /**
     * 获取view在adapter中的position
     *
     * @param view
     * @return
     */
    private int getViewPosition(View view) {
        int position = 0;
        Object tag = view.getTag();
        if (tag instanceof RecyclerHolder) {
            RecyclerHolder vh = (RecyclerHolder) tag;
            position = vh.getAdapterPosition();
        }

        return position;
    }

    @Override
    public void onClick(View v) {
        int position = getViewPosition(v);
        onItemClickListener.onItemClick(v, position, getItem(position));
    }

    @Override
    public boolean onLongClick(View v) {
        int position = getViewPosition(v);
        onItemLongClickListener.onItemLongClick(v, position, getItem(position));
        return true;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        // 布局是GridLayoutManager时，控制header和footer显示一行
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isFullItemViewType(position))
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        // 布局是StaggeredGridLayoutManager时，控制header和footer显示一行
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            if (isFullItemViewType(holder.getLayoutPosition())) {
                p.setFullSpan(true);
            } else {
                p.setFullSpan(false);
            }
        }
    }

    /**
     * 当前item是否显示独占一行或一列
     *
     * @param position 对应的位置
     * @return
     */
    protected boolean isFullItemViewType(int position) {
        return (getItemViewType(position) == ITEM_TYPE_EMPTY);
    }
}
