package com.codereader.samusko.codereader.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.codereader.samusko.codereader.R;
import com.codereader.samusko.codereader.fragments.NavigationDrawerFragment;
import com.codereader.samusko.codereader.fragments.UserForkedReposFragment;
import com.codereader.samusko.codereader.fragments.UserPublicActivitiesFragment;
import com.codereader.samusko.codereader.fragments.UserReposFragment;
import com.codereader.samusko.codereader.model.GitHubUser;
import com.codereader.samusko.codereader.model.Repo;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private NavigationDrawerFragment navigationDrawerFragment;
    private UserReposFragment userRepoFragment;
    private UserForkedReposFragment forkedReposFragment;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        context = this;
        initViews();
        new TaskLoadUserData().execute();
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar_user_activity);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        navigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_nav_drawer);

        navigationDrawerFragment.setUp(R.id.fragment_nav_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


        CircleImageView circleImageView = (CircleImageView) findViewById(R.id.nav_drawer_avatar);

        viewPager = (ViewPager) findViewById(R.id.view_pager_user_activity);
        viewPager.clearOnPageChangeListeners();
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        setupViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id) {
            case android.R.id.home:
                navigationDrawerFragment.openDrawer();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // adding fragment of user repositories
        userRepoFragment = new UserReposFragment();
        //userRepos.setUpData(user);
        // userRepos.setUp(userLogin);
        adapter.addFragment(userRepoFragment, getResources().getString(R.string.tab_user_repos));

        forkedReposFragment = new UserForkedReposFragment();
        //forkedReposFragment.setUpData(user);
        adapter.addFragment(forkedReposFragment, getResources().getString(R.string.tab_user_forked_repos));

        adapter.addFragment(new UserPublicActivitiesFragment(), getResources().getString(R.string.tab_user_public_activities));

        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout_user_activity);
        tabLayout.setupWithViewPager(viewPager);
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

    class TaskLoadUserData extends AsyncTask<Void, Void, GitHubUser> {
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
        protected GitHubUser doInBackground(Void... params) {

            GitHubUser user = new GitHubUser(getApplicationContext());
            user.loadForkedRepos();
            //setupViewPager(viewPager, user);
            return user;
        }

        @Override
        protected void onPostExecute(GitHubUser user) {
            super.onPostExecute(user);

            loadDataToFragments(user);
            progressDialog.dismiss();
        }
    }

    private void loadDataToFragments(GitHubUser user) {
        navigationDrawerFragment.setUpData(user);
        forkedReposFragment.setUpData(user);
        userRepoFragment.setUpData(user);
    }
}
