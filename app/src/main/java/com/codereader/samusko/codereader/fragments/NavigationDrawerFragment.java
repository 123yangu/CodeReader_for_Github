package com.codereader.samusko.codereader.fragments;


import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codereader.samusko.codereader.R;
import com.codereader.samusko.codereader.activities.UserActivity;
import com.codereader.samusko.codereader.adapters.RVAdapterNavDrawer;
import com.codereader.samusko.codereader.model.GitHubUser;
import com.codereader.samusko.codereader.model.NavDrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private RVAdapterNavDrawer adapter;
    private GitHubUser user;

    private TextView txtLogin;
    private TextView txtFollowings;
    private TextView txtFollowers;
    private TextView txtJoinedOn;
    private ImageView imgAvatar;


    public NavigationDrawerFragment() {
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
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        initViews(view);
        return view;
    }

    private void initViews(View view) {

        txtLogin = (TextView) view.findViewById(R.id.nav_drawer_login);
        txtFollowings = (TextView) view.findViewById(R.id.nav_drawer_followings);
        txtFollowers = (TextView) view.findViewById(R.id.nav_drawer_followers);
        txtJoinedOn = (TextView) view.findViewById(R.id.nav_drawer_joined_date);
        imgAvatar = (ImageView) view.findViewById(R.id.nav_drawer_avatar);

        recyclerView = (RecyclerView) view.findViewById(R.id.nav_drawer_recycler_view);
        adapter = new RVAdapterNavDrawer(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    public void setUpData(GitHubUser user) {
        this.user = user;
        setupDataToViews();
    }

    private void setupDataToViews() {
        // setting data to views
        txtLogin.setText(user.getLogin());
        txtFollowers.setText(String.valueOf(user.getFollowers()) + "\nfollowers");
        txtFollowings.setText(String.valueOf(user.getFollowing()) + "\nfollowing");
        txtJoinedOn.setText(user.getJoinedData());

        Drawable avatar = new BitmapDrawable(user.getUserAvatar());
        imgAvatar.setImageDrawable(avatar);
    }

    private List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();

        int[] iconIds;

        TypedArray typedArray = getActivity().getResources().obtainTypedArray(R.array.nav_drawer_menu_icons);

        int count = typedArray.length();
        iconIds = new int[count];

        for (int i = 0; i < iconIds.length; i++) {
            iconIds[i] = typedArray.getResourceId(i, 0);
        }


        String[] titles = getActivity().getResources().getStringArray(R.array.nav_drawer_menu_titles);

        for (int i = 0; i < iconIds.length && i < titles.length; i++) {
            NavDrawerItem item = new NavDrawerItem();
            item.setIconId(iconIds[i]);
            item.setTitle(titles[i]);

            data.add(item);
        }

        return data;
    }

    public void setUp(int fragment_nav_drawer, DrawerLayout drawerLayout, Toolbar toolbar) {
        mDrawerLayout = drawerLayout;

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.nav_drawer_open, R.string.nav_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }
}
