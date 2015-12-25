package com.codereader.samusko.codereader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codereader.samusko.codereader.R;
import com.codereader.samusko.codereader.model.NavDrawerItem;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Bohdan on 02.12.2015.
 */
public class RVAdapterNavDrawer extends RecyclerView.Adapter<RVAdapterNavDrawer.ViewHolderNav> {
    LayoutInflater inflater;
    Context context;
    List<NavDrawerItem> data = Collections.emptyList();

    public RVAdapterNavDrawer(Context context, List<NavDrawerItem> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolderNav onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_nav_drawer, parent, false);

        ViewHolderNav viewHolderNav = new ViewHolderNav(view);

        return viewHolderNav;
    }

    @Override
    public void onBindViewHolder(ViewHolderNav holder, int position) {

        NavDrawerItem item = data.get(position);
        holder.title.setText(item.getTitle());
        holder.icon.setImageResource(item.getIconId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolderNav extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView title;
        View itemContainer;

        public ViewHolderNav(View itemView) {
            super(itemView);

            itemContainer = itemView.findViewById(R.id.item_row_container);
            icon= (ImageView) itemView.findViewById(R.id.item_image);
            title = (TextView) itemView.findViewById(R.id.item_title);

        }
    }
}
