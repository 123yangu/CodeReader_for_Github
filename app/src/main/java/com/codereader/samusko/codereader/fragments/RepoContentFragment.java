package com.codereader.samusko.codereader.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.codereader.samusko.codereader.R;
import com.codereader.samusko.codereader.activities.FileActivity;
import com.codereader.samusko.codereader.adapters.RVAdapterRepoContent;
import com.codereader.samusko.codereader.adapters.RecyclerViewClickListener;
import com.codereader.samusko.codereader.api.RetrofitManager;
import com.codereader.samusko.codereader.model.Branch;
import com.codereader.samusko.codereader.model.RepoContent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepoContentFragment extends Fragment {
    RecyclerView recyclerView;
    RVAdapterRepoContent adapter;
    TextView txtPathContent;
    LinearLayout linLayPath;

    ArrayList<RepoContent> contentsList = new ArrayList<>();
    ArrayList<String> pathList = new ArrayList<>();
    ArrayList<String> listHistoryStack = new ArrayList<>();
    ArrayList<Branch> listBranches = new ArrayList<>();
    ArrayAdapter<String> spinnerAdapter;
    List<String> spinnerCategories = new ArrayList<>();

    String repoName;
    Spinner spinner;
    String branch = "master";

    public RepoContentFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TaskLoadRepoContent taskLoad = new TaskLoadRepoContent();
        taskLoad.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_content, container, false);

        initViews(view);
        return view;
    }

    private void initViews(View view) {

        initSpinner(view);

        linLayPath = (LinearLayout) view.findViewById(R.id.lin_lay_path_navigation);

        ImageView imgBack = (ImageView) view.findViewById(R.id.img_content_back);
        imgBack.setOnClickListener(imgBackClickListener);

        txtPathContent = (TextView) view.findViewById(R.id.contents_path);
        notifyTextViewPathContent();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_repo_contents);
        adapter = new RVAdapterRepoContent(getActivity(), contentsList, getItemListener());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void initSpinner(View view) {
        // Spinner element
        spinner = (Spinner) view.findViewById(R.id.spinner_branch);

        // Creating adapter for spinner
        spinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, spinnerCategories);
        spinner.setOnItemSelectedListener(spinnerClick);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

    }

    AdapterView.OnItemSelectedListener spinnerClick = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedValue = spinnerAdapter.getItem(position).toString();
            branch = selectedValue;

            new TaskLoadRepoContent().execute();

            Log.i("ItemSelectedContent", "Position = " + position + " name = " + selectedValue);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    //handlong click on ImageBack
    View.OnClickListener imgBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pathListStepBack();
        }
    };

    //handling recycler view click
    private RecyclerViewClickListener getItemListener() {
        RecyclerViewClickListener itemListener = new RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked(View view, int position) {
                RepoContent content = contentsList.get(position);
                String path = content.getPath();
                String name = content.getName();
                String download_url = content.getDownload_url();

                if (content.getType().equals("dir")) {
                    // getting path for clicked content of repo
                    pathList.add(path);
                    listHistoryStack.add(name);

                    new TaskLoadRepoContent().execute();
                } else {
                    Intent intent = new Intent(getActivity(), FileActivity.class);
                    intent.putExtra("file_name", name);
                    intent.putExtra("url", download_url);
                    getActivity().startActivity(intent);
                }
            }
        };

        return itemListener;
    }

    public void setupData(String repo_name) {
        pathList.add("");
        this.repoName = repo_name;

    }

    public void setupDataToSpinner(ArrayList<Branch> branches) {
        // listBranches.addAll(branches);
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


    public void pathListStepBack() {
        if (pathList.size() == 2) {
            linLayPath.setVisibility(View.GONE);

        }
        if (pathList.size() > 1) {
            pathList.remove(pathList.size() -                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             1);
            listHistoryStack.remove(listHistoryStack.size() - 1);
            new TaskLoadRepoContent().execute();

        } else {
            getActivity().finish();
        }
    }

    private void notifyTextViewPathContent() {

        if (linLayPath.getVisibility() == View.GONE && pathList.size()>1) {
            linLayPath.setVisibility(View.VISIBLE);
        }

        StringBuffer buffer = new StringBuffer();
        txtPathContent.setText("");
        for (String item : listHistoryStack) {
            buffer.append(item + "/");
        }
        txtPathContent.setText(buffer);
    }

    class TaskLoadRepoContent extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Please wait. Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            contentsList.clear();

            if (pathList.get(pathList.size() - 1).equals("")) {
                contentsList.addAll(RetrofitManager.getRepoContent(getActivity(), repoName, branch));
            } else {
                contentsList.addAll(RetrofitManager.getRepoContentByPath(getActivity(), repoName, pathList.get(pathList.size() - 1), branch));
            }

            sortList(contentsList);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
                // notify displaying the path
                notifyTextViewPathContent();
            }
            progressDialog.dismiss();
        }
    }

    private void sortList(ArrayList<RepoContent> contentsList) {

        for (int i = 0; i < contentsList.size() - 1; i++) {
            int index = i;
            for (int j = i + 1; j < contentsList.size(); j++)
                if (contentsList.get(j).getType().equals("dir") && contentsList.get(index).getType().equals("file"))
                    index = j;

            RepoContent repoContent = contentsList.get(index);
            contentsList.set(index, contentsList.get(i));
            contentsList.set(i, repoContent);
        }

    }
}

