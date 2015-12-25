package com.codereader.samusko.codereader.fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codereader.samusko.codereader.R;
import com.codereader.samusko.codereader.adapters.RVAdapterReposList;
import com.codereader.samusko.codereader.model.GitHubUser;
import com.codereader.samusko.codereader.model.Repo;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserForkedReposFragment extends Fragment {
    ArrayList<Repo> forkedRepos = new ArrayList<>();
    RecyclerView recyclerView;
    RVAdapterReposList adapter;
    GitHubUser user;

    public UserForkedReposFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //new TaskLoadData().execute();
        //setUpData(user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_forked_repos, container, false);

        initViews(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void initViews(View view) {


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_repos_forked);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RVAdapterReposList(getActivity(), forkedRepos);
        recyclerView.setAdapter(adapter);

    }

    public void setUpData(GitHubUser user) {
        this.user = user;
        setUpDataToViews();

    }

    private void setUpDataToViews() {
        forkedRepos.addAll(user.getForkedRepos());
        adapter.notifyDataSetChanged();
    }
}
