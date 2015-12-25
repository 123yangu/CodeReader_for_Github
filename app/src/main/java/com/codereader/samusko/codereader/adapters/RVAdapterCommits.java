package com.codereader.samusko.codereader.adapters;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codereader.samusko.codereader.R;
import com.codereader.samusko.codereader.model.Commit;

import java.util.Collections;
import java.util.List;

public class RVAdapterCommits  extends RecyclerView.Adapter<RVAdapterCommits.ViewHolderCommit>{
    Context context;
    LayoutInflater inflater;
    List<Commit> data = Collections.emptyList();
    RecyclerViewClickListener itemListener;

    public RVAdapterCommits(Context context, List<Commit> data, RecyclerViewClickListener itemListener) {
        this.context = context;
        this.data = data;
        this.itemListener = itemListener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolderCommit onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_commit, parent, false);

        ViewHolderCommit viewHolderCommit = new ViewHolderCommit(view);

        return viewHolderCommit;
    }

    @Override
    public void onBindViewHolder(ViewHolderCommit holder, int position) {
        Commit commit = data.get(position);

        holder.imgAvatar.setImageDrawable(new BitmapDrawable(commit.getAuthorAvatar())); // setting avatar
        holder.txtMessage.setText(commit.getCommitMessage());
        holder.txtNameAndDate.setText(commit.getAuthorName()+" committed on "+commit.getCommitDate());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolderCommit extends RecyclerView.ViewHolder{
        View container;
        ImageView imgAvatar;
        TextView txtMessage;
        TextView txtNameAndDate;

        public ViewHolderCommit(View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.item_commits_container);
            imgAvatar = (ImageView) itemView.findViewById(R.id.image_commit_author);
            txtMessage = (TextView) itemView.findViewById(R.id.text_commit_message);
            txtNameAndDate = (TextView) itemView.findViewById(R.id.text_commit_author_name_data);


        }
    }
}
