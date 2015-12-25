package com.codereader.samusko.codereader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codereader.samusko.codereader.R;
import com.codereader.samusko.codereader.model.RepoContent;

import java.util.Collections;
import java.util.List;

/**
 * Created by Bohdan on 10.12.2015.
 */
public class RVAdapterRepoContent extends RecyclerView.Adapter<RVAdapterRepoContent.ViewHolderContent> {

    private Context context;
    private List<RepoContent> data = Collections.emptyList();
    LayoutInflater inflater;
    RecyclerViewClickListener itemListener;

    public RVAdapterRepoContent(Context context, List<RepoContent> data, RecyclerViewClickListener itemListener) {
        this.context = context;
        this.data = data;
        this.itemListener = itemListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolderContent onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_repo_content, parent, false);

        ViewHolderContent holder = new ViewHolderContent(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderContent holder, int position) {
        RepoContent content = data.get(position);

        if(content.getType().equals("dir")){
            holder.imgType.setImageResource(R.drawable.ic_directory);
        }else {
            holder.imgType.setImageResource(R.drawable.ic_file);
        }
        holder.txtName.setText(content.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolderContent extends RecyclerView.ViewHolder implements View.OnClickListener{
        View container;
        ImageView imgType;
        TextView txtName;
        public ViewHolderContent(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.item_content_container);
            container.setOnClickListener(this);
            imgType = (ImageView) itemView.findViewById(R.id.imageViewContenType);
            txtName = (TextView) itemView.findViewById(R.id.textViewContentName);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, getLayoutPosition());
        }
    }
}
