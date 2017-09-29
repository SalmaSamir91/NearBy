package com.example.cognitev.nearby.nearbylist.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cognitev.nearby.nearbylist.model.dto.NearbyItemModel;
import com.example.cognitev.nearby.R;

import java.util.ArrayList;

/**
 * Created by Salma on 9/21/2017.
 */

public class NearbyListAdapter extends RecyclerView.Adapter<NearbyListAdapter.NearbyItemViewHolder> {

    private ArrayList<NearbyItemModel> nearbyItems;
    private Context context;
    private NearbyItemModel mItem;
    private int lastPosition = -1;

    public NearbyListAdapter(ArrayList<NearbyItemModel> nearbyItems, Context context) {
        this.context = context;
        this.nearbyItems = nearbyItems;
    }


    @Override
    public NearbyItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nearby,null);
        NearbyItemViewHolder nearbyItemViewHolder = new NearbyItemViewHolder(view);
        return nearbyItemViewHolder;
    }

    @Override
    public void onBindViewHolder(final NearbyItemViewHolder holder, int position) {
        mItem = nearbyItems.get(position);


        Glide.with(context)
                .load(mItem.getUrl())
                .placeholder(R.drawable.app_icon)
                .fitCenter()
                .into(holder.img);

        holder.title.setText(mItem.getName());
        if(mItem.getLocation().getAddress() != null && !mItem.getLocation().getAddress().isEmpty()){
            holder.shortDesc.setVisibility(View.VISIBLE);
            holder.shortDesc.setText(mItem.getLocation().getAddress());
        } else{
            holder.shortDesc.setVisibility(View.GONE);
        }

        setAnimation(holder.itemView, position);
    }


    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return (null != nearbyItems ? nearbyItems.size() : 0);
    }


    public class NearbyItemViewHolder extends RecyclerView.ViewHolder {

        protected ImageView img;
        protected TextView title;
        protected TextView shortDesc;

        public NearbyItemViewHolder(View itemView) {
            super(itemView);
            this.img = (ImageView) itemView.findViewById(R.id.image);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.shortDesc = (TextView) itemView.findViewById(R.id.description);
        }
    }
    public void addItems(ArrayList<NearbyItemModel> items) {
        nearbyItems.clear();
        nearbyItems.addAll(items);
        notifyDataSetChanged();
    }

    public void insert( NearbyItemModel model) {
        nearbyItems.add(model);
        notifyDataSetChanged();
    }

    public void clearItems() {
        nearbyItems.clear();
        notifyDataSetChanged();
    }
}
