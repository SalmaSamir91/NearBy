package com.example.cognitev.nearby.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Salma on 9/21/2017.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    protected T mBusinessObject ;

    public void bindData(T businessObject , int position) {
        this.mBusinessObject = businessObject ;
        this.bindViewData(businessObject, position);
    }

    public abstract void bindViewData(T businessObject, int position);
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

}
