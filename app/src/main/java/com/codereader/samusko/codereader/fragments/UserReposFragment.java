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

public class UserReposFragment extends Fragment {

    RecyclerView recyclerView;
    RVAdapterReposList customAdapter;
    ArrayList<Repo> repoArrayList = new ArrayList<>();
    GitHubUser user;

    //String userLogin;
    public UserReposFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_repos, container, false);

        initViews(view);
        return view;
    }

    private void initViews(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_repos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        customAdapter = new RVAdapterReposList(getActivity(), repoArrayList);
        recyclerView.setAdapter(customAdapter);

    }

    public void setUpData(GitHubUser user) {
        this.user = user;
        setUpDataToViews();
    }

    private void setUpDataToViews() {
        repoArrayList.addAll(user.getUserOnlyRepos());
        customAdapter.notifyDataSetChanged();
    }

}
