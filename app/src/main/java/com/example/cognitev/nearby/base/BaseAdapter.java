package com.example.cognitev.nearby.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salma on 9/21/2017.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected Context mContext;
    protected List<T> mlstData = new ArrayList<T>();;


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(getHolderResourceId(), parent, false);
        return getHolderInstance(view);
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
            T obj = mlstData.get(position);
            holder.bindData(obj , position);
    }

    public void add(List<T> items) {
        int previousDataSize = this.mlstData.size();
        this.mlstData.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }

    public void addToTop(List<T> items) {
        int previousDataSize = this.mlstData.size();
        this.mlstData.addAll(0 ,items);
        notifyDataSetChanged();
    }

    public void addToTop(T item) {
        int previousDataSize = this.mlstData.size();
        this.mlstData.add(0,item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.mlstData.size() ;
    }


    public List<T> getDataList() {
        return this.mlstData;
    }

    public void setDataList(List<T> data) {
        this.mlstData = data;
        this.notifyDataSetChanged();
    }

    public void addItems(List<T> data) {
        this.mlstData.clear();
        this.mlstData.addAll(data);
        notifyDataSetChanged();
    }


    public void clearItems() {
        this.mlstData.clear();
        notifyDataSetChanged();
    }

    protected abstract BaseViewHolder getHolderInstance(View view);
    protected abstract int getHolderResourceId();

}
