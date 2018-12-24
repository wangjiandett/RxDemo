package com.moa.rxdemo.base.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * HodlderAdapter 实现ViewHolder复用
 *
 * @param <V>
 */
public abstract class HolderAdapter<V> extends BaseAdapter {
    private HashSet<ViewHolder<V>> holders = new HashSet<ViewHolder<V>>();

    private Context context;
    private List<V> mList = new ArrayList<>();

    protected HolderAdapter(Context context) {
        this.context = context;
    }
    
    public void setList(List<V> list) {
        this.mList = list;
    }
    
    public void setListAndNotify(List<V> list){
        setList(list);
        notifyDataSetChanged();
    }
    
    public List<V> getList() {
        return mList;
    }
    
    @Override
    public int getCount() {
        return mList.size();
    }
    
    @Override
    public V getItem(int position){
        return mList.get(position);
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    /**
     * ListView 或 GridView 调用<br/>
     * setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE)<br/>
     * setChoiceMode(AbsListView.CHOICE_MODE_SINGLE)<br/>
     * 时使用,({@link ListAdapter#hasStableIds()} == {@code true})<br/>
     * 重写此方法使getCheckedItemIds()有效
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }
    
    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {

        V obj = getItem(position);

        ViewHolder<V> holder;
        View view;
        if (convertView == null || convertView.getTag() == null) {
            holder = createHolder(position, obj);
            view = holder.init(obj, parent, context);
            view.setTag(holder);
            holders.add(holder);
        } else {
            holder = (ViewHolder<V>) convertView.getTag();
            view = convertView;
        }

        onBindViewHolder(holder, obj, position, context);

        return view;
    }

    public void onMovedToScrapHeap(View view) {
        if (view.getTag() instanceof ViewHolder) {
            ((ViewHolder) view.getTag()).unbind(false);
        }
    }

    public void dispose() {
        for (ViewHolder holder : holders) {
            holder.unbind(true);
        }
    }
    
    protected void onBindViewHolder(ViewHolder<V> holder, V obj, int position, Context context) {
        holder.bind(obj, position, context);
    }

    protected abstract ViewHolder<V> createHolder(int position, V obj);
}
