package com.codereader.samusko.codereader.fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codereader.samusko.codereader.R;
import com.codereader.samusko.codereader.adapters.RVAdapterBranches;
import com.codereader.samusko.codereader.model.Branch;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BranchesFragment extends Fragment {
    RecyclerView recyclerView;
    RVAdapterBranches adapter;
    ArrayList<Branch> branchesList = new ArrayList<>();


    public BranchesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branches, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_branches);
        adapter = new RVAdapterBranches(getActivity(), branchesList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void setupData(ArrayList<Branch> branches) {
        this.branchesList.addAll(branches);

    }
}
