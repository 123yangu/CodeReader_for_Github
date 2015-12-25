package com.codereader.samusko.codereader.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.codereader.samusko.codereader.R;
import com.codereader.samusko.codereader.adapters.RVAdapterCommits;
import com.codereader.samusko.codereader.adapters.RecyclerViewClickListener;
import com.codereader.samusko.codereader.api.RetrofitManager;
import com.codereader.samusko.codereader.model.Branch;
import com.codereader.samusko.codereader.model.Commit;

import java.util.ArrayList;
import java.util.Collections;

public class CommitsFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Commit> commitsList = new ArrayList<>();
    RVAdapterCommits adapter;
    ProgressDialog progressDialog;
    private ArrayAdapter<String> spinnerAdapter;
    private Spinner spinner;
    private ArrayList<String> spinnerCategories = new ArrayList<>();
    private String repoName;


    public CommitsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Creating adapter for spinner
        spinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, spinnerCategories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_commits, container, false);

        initViews(view);
        return view;
    }

    private void initViews(View view) {
        // handling recycler view item click
        RecyclerViewClickListener itemClickListener = new RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked(View view, int position) {

            }
        };

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_commits);
        adapter = new RVAdapterCommits(getActivity(), commitsList, itemClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        initSpinner(view);
    }

    public void setupData(String repoName, Context context) {
        this.repoName = repoName;
        progressDialog = new ProgressDialog(context);

        new TaskLoadCommits().execute(repoName, "master");
    }

    private void initSpinner(View view) {
        // Spinner element
        spinner = (Spinner) view.findViewById(R.id.spinner_branches_filter_for_commits);

        spinner.setOnItemSelectedListener(spinnerClick);


        spinner.setAdapter(spinnerAdapter);

    }

    AdapterView.OnItemSelectedListener spinnerClick = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedValue = spinnerAdapter.getItem(position).toString();
            new TaskLoadCommits().execute(repoName, selectedValue);

            Log.i("ItemSelected", "Position = " +position+" name = "+ selectedValue);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void setupDataToSpinner(ArrayList<Branch> branches) {
        for (Branch branch : branches) {
            spinnerCategories.add(branch.getName());
        }

        for (int i = 0; i < spinnerCategories.size(); i++) {
            if (spinnerCategories.get(i).toString().equals("master")) {
                Collections.swap(spinnerCategories, 0, i);
            }
        }

        spinnerAdapter.notifyDataSetChanged();
    }

    class TaskLoadCommits extends AsyncTask<String, Void, Void> {
//        ProgressDialog progressDialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Please wait. Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            String repo_name = params[0];
            String branch =  params[1]; //such as branch "master" is by default

            commitsList.clear();
            commitsList.addAll(RetrofitManager.getCommits(getActivity(), repo_name, branch));

            // downloading all avatars for all commits
            for (Commit commit : commitsList) {
                commit.loadAuthorAvatar(getActivity());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
            progressDialog.dismiss();
        }
    }


}
