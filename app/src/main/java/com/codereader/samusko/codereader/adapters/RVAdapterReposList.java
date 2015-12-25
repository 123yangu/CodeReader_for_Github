package com.codereader.samusko.codereader.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codereader.samusko.codereader.R;
import com.codereader.samusko.codereader.activities.RepoActivity;
import com.codereader.samusko.codereader.model.Repo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Bohdan on 27.11.2015.
 */
public class RVAdapterReposList extends RecyclerView.Adapter<RVAdapterReposList.RepoViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<Repo> data = Collections.emptyList();

    public RVAdapterReposList(Context context, ArrayList<Repo> dataList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = dataList;
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_user_repo, parent, false);

        RepoViewHolder repoViewHolder = new RepoViewHolder(view);
        return repoViewHolder;
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {

        Repo repo = data.get(position);

        holder.txtName.setText(repo.getName());
        holder.txtLastUpdate.setText(repo.getLastUpdate());
        holder.txtForkCount.setText(String.valueOf(repo.getForks()));
        holder.txtStarsCount.setText(String.valueOf(repo.getStargazers_count()));
        holder.txtLanguage.setText(repo.getLanguage());


        if (repo.getFork() == "true") {
            holder.containerForkedInfo.setVisibility(View.VISIBLE);
            holder.txtForkedFrom.setText(repo.getForkedFrom());
        }

        if (repo.getDescription().length() == 0)
            holder.txtDescription.setVisibility(View.GONE);
        else
            holder.txtDescription.setText(repo.getDescription());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View container;
        TextView txtName;
        TextView txtDescription;
        TextView txtLastUpdate;
        TextView txtStarsCount;
        TextView txtForkCount;
        TextView txtLanguage;

        View containerForkedInfo;
        TextView txtForkedFrom;

        public RepoViewHolder(View itemView) {

            super(itemView);

            container = itemView.findViewById(R.id.item_container);
            txtName = (TextView) itemView.findViewById(R.id.item_repo_name);
            txtDescription = (TextView) itemView.findViewById(R.id.item_description);
            txtLastUpdate = (TextView) itemView.findViewById(R.id.item_last_repo_update);
            txtStarsCount = (TextView) itemView.findViewById(R.id.item_star_count);
            txtForkCount = (TextView) itemView.findViewById(R.id.item_forks_count);
            txtLanguage = (TextView) itemView.findViewById(R.id.item_language);

            txtForkedFrom = (TextView) itemView.findViewById(R.id.item_forked_from);
            containerForkedInfo = itemView.findViewById(R.id.container_fork);

            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, RepoActivity.class);

            intent.putExtra("repo_name", String.valueOf(data.get(getPosition()).getName()));

            context.startActivity(intent);
        }
    }
}
