package com.codereader.samusko.codereader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codereader.samusko.codereader.R;
import com.codereader.samusko.codereader.model.Branch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Bohdan on 14.12.2015.
 */
public class RVAdapterBranches extends RecyclerView.Adapter<RVAdapterBranches.ViewHolderBranch> {
    Context context;
    LayoutInflater inflater;
    List<Branch> data = Collections.emptyList();
    RecyclerViewClickListener itemListener;

    public RVAdapterBranches(Context context, ArrayList<Branch> branchesList) {
        this.context = context;
        data = branchesList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolderBranch onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_branch, parent, false);
        ViewHolderBranch viewHolderBranch = new ViewHolderBranch(view);

        return viewHolderBranch;
    }

    @Override
    public void onBindViewHolder(ViewHolderBranch holder, int position) {
        Branch branch = data.get(position);

        holder.txtName.setText(branch.getName());
        holder.txtDescription.setText(branch.getDescription());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolderBranch extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtDescription;

        public ViewHolderBranch(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.item_branch_name);
            txtDescription = (TextView) itemView.findViewById(R.id.item_branch_update_updater);

        }
    }
}
