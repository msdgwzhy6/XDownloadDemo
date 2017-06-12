package com.xm.frame.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author: 小民
 * @date: 2017-06-03
 * @time: 01:16
 * @说明: RecyclerView 专用适配器 -> 只针对单布局
 */
public class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    /* 打气筒 */
    private LayoutInflater mInflater;
    /* 绑定布局 */
    private int layoutId;
    /* variableId 也就是 DataBinding 压入的时候使用 */
    private int variableId;
    /* Data */
    private List<T> data;

    public CommonAdapter(Context context, int layoutId, int variableId, List<T> data) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
        this.variableId = variableId;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(mInflater, layoutId, parent, false);
        return new ViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        T t = data.get(position);
        holder.getmBinding().setVariable(variableId,t);
        holder.getmBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /* 批量新增数据 -> 推荐这种高效 */
    public void addAll(List<T> list){
        int positionStart = data.size();
        data.addAll(list);
        notifyItemRangeInserted(positionStart,list.size());
    }

    /* 新增数据 -> 推荐这种高效 */
    public void add(T t){
        data.add(t);
        notifyItemInserted(data.size());
    }

    /* 删除数据 -> 推荐这种高效 */
    public void remove(int position){
        data.remove(position);
        notifyItemRemoved(position);
    }


}
