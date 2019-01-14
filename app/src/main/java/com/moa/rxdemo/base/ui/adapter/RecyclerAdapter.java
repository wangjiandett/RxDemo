package com.moa.rxdemo.base.ui.adapter;

import android.content.Context;
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
 * 封装对RecyclerView adapter的简单使用
 * <p>
 * Created by：wangjian on 2019/1/8 17:29
 */
public abstract class RecyclerAdapter<D, VH extends RecyclerHolder<D>> extends RecyclerView.Adapter<VH> implements
        View.OnClickListener, View.OnLongClickListener {

    public static final int ITEM_TYPE_NORMAL = 0;
    /**
     * 在enableHeader为true时设置head才有效
     */
    public static final int ITEM_TYPE_HEAD = -1;
    /**
     * 在enableFooter为true时设置head才有效
     */
    public static final int ITEM_TYPE_FOOT = -2;


    private View mHeaderView;
    private View mFooterView;

    private OnItemClickListener<D> onItemClickListener;
    private OnItemLongClickListener<D> onItemLongClickListener;

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(Context context, int layoutId) {
        setHeaderView(getLayout(context, layoutId));
    }

    public void setHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
        notifyItemInserted(0);
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setFooterView(Context context, int layoutId) {
        setFooterView(getLayout(context, layoutId));
    }

    public void setFooterView(View mFooterView) {
        this.mFooterView = mFooterView;
        notifyItemInserted(getItemCount() - 1);
    }

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

    //-----------------------------以下对数据集合操作-----------------------------------
    /**
     * 数据源
     */
    private List<D> mDataList;

    public List<D> getDataList() {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        return mDataList;
    }

    public void setDataList(List<D> mDataList) {
        this.mDataList = mDataList;
    }

    public void remove(D item) {
        int index = getDataList().indexOf(item);
        remove(index);
    }

    public void remove(int index) {
        getDataList().remove(index);
        notifyItemRemoved(index);
    }

    public void add(D item, int index) {
        getDataList().add(item);
        notifyItemChanged(index);
    }

    public void add(D item) {
        getDataList().add(item);
        int index = getDataList().indexOf(item);
        notifyItemChanged(index);
    }

    public void addAll(List<D> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        getDataList().addAll(dataList);
        notifyItemRangeChanged(getDataList().size() - dataList.size(), getDataList().size());
    }

    public void addAll(int index, List<D> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        getDataList().addAll(index, dataList);
        notifyItemRangeChanged(index, index + dataList.size());
    }

    public void clear() {
        getDataList().clear();
        notifyDataSetChanged();
    }

    public D getItem(int position) {
        if (getDataList().size() > position && position > -1) {
            return getDataList().get(position);
        }
        return null;
    }

    public View getLayout(Context context, int layoutRes) {
        return LayoutInflater.from(context).inflate(layoutRes, null);
    }

    public int getDataCount() {
        return getDataList().size();
    }

    @Override
    public int getItemViewType(int position) {
        // 头部
        if (mHeaderView != null && position == 0) {
            return ITEM_TYPE_HEAD;
        }
        // 尾部
        else if (mFooterView != null && position == getItemCount() - 1) {
            return ITEM_TYPE_FOOT;
        }
        // 正常部分
        else {
            // ITEM_TYPE_NORMAL 默认是0
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        if (mHeaderView != null && mFooterView != null) {
            return getDataCount() + 2;
        } else if (mHeaderView != null) {// && mFooterView == null
            return getDataCount() + 1;
        } else if (mFooterView != null) {// mHeaderView == null &&
            return getDataCount() + 1;
        } else {
            return getDataCount();
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        VH holder = getHolder(viewGroup.getContext(), viewType);
        bindListener(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH recyclerHolder, int position) {
        if (getItemViewType(position) == ITEM_TYPE_FOOT || getItemViewType(position) == ITEM_TYPE_HEAD) {
            return;
        }
        position = getRealPosition(recyclerHolder);
        onBindViewHolder(getItem(position), position, recyclerHolder);
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();
        return mHeaderView == null ? position : position - 1;
    }

    protected void bindListener(VH holder) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(this);
        }

        if (onItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(this);
        }
    }

    private int getViewPosition(View view) {
        int position = 0;
        Object tag = view.getTag();
        if (tag instanceof RecyclerHolder) {
            VH vh = (VH) tag;
            position = getRealPosition(vh);
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

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (getItemViewType(position) == ITEM_TYPE_HEAD
                            || getItemViewType(position) == ITEM_TYPE_FOOT)
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull VH holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            if (getItemViewType(holder.getLayoutPosition()) == ITEM_TYPE_HEAD
                    || getItemViewType(holder.getLayoutPosition()) == ITEM_TYPE_FOOT) {
                p.setFullSpan(true);
            } else {
                p.setFullSpan(false);
            }
        }
    }

    /**
     * 设置item holder，当<br/>
     * {@link #ITEM_TYPE_HEAD} == viewType, 在enableHeader为true时设置head才有效<br/>
     * {@link #ITEM_TYPE_FOOT} == viewType, 在enableFooter为true时设置foot才有效<br/>
     *
     * @param context  context
     * @param viewType item view 类型
     * @return
     */
    protected abstract VH getHolder(Context context, int viewType);

    protected abstract void onBindViewHolder(D data, int position, VH holder);


}
