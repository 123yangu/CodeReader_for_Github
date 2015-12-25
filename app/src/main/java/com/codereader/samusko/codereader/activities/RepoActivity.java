package com.codereader.samusko.codereader.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.codereader.samusko.codereader.R;
import com.codereader.samusko.codereader.api.RetrofitManager;
import com.codereader.samusko.codereader.fragments.BranchesFragment;
import com.codereader.samusko.codereader.fragments.CommitsFragment;
import com.codereader.samusko.codereader.fragments.RepoContentFragment;
import com.codereader.samusko.codereader.model.Branch;

import java.util.ArrayList;
import java.util.List;

public class RepoActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    RepoContentFragment contentFragment;
    CommitsFragment commitsFragment;
    BranchesFragment branchesFragment;
    String repoName;
    ArrayList<Branch> branchesList = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);

        initViews();

    }

    private void initFragments() {

        contentFragment = new RepoContentFragment();
        contentFragment.setupData(repoName);

        commitsFragment = new CommitsFragment();
        commitsFragment.setupData(repoName, this);


        branchesFragment = new BranchesFragment();
        branchesFragment.setupData(branchesList);

    }

    private void initViews() {
        context = this;
        repoName = getIntent().getStringExtra("repo_name");

        new TaskLoadData().execute();

        initFragments();

        String repoName = getIntent().getStringExtra("repo_name");

        toolbar = (Toolbar) findViewById(R.id.activity_repo_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(repoName);


        viewPager = (ViewPager) findViewById(R.id.view_pager_repo_activity);
        viewPager.clearOnPageChangeListeners();
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        setupViewPager();

    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(contentFragment, getResources().getString(R.string.tab_repo_files));

        adapter.addFragment(commitsFragment, getResources().getString(R.string.tab_repo_all_commits));

        adapter.addFragment(branchesFragment, getResources().getString(R.string.tab_repo_branches));

        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout_repo_activity);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_repo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.finish();
        }

        if (id == R.id.home) {
            contentFragment.pathListStepBack();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        ///super.onBackPressed();
        contentFragment.pathListStepBack();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    class TaskLoadData extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Please wait. Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            branchesList.addAll(RetrofitManager.getBranches(context, repoName));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            branchesFragment.setupData(branchesList);
            contentFragment.setupDataToSpinner(branchesList);
            commitsFragment.setupDataToSpinner(branchesList);

            progressDialog.dismiss();
        }
    }
}
