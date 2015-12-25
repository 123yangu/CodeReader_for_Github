package com.codereader.samusko.codereader.api;

import android.content.Context;
import android.util.Base64;

import com.codereader.samusko.codereader.model.Account;
import com.codereader.samusko.codereader.model.Branch;
import com.codereader.samusko.codereader.model.Commit;
import com.codereader.samusko.codereader.model.GitHubUser;
import com.codereader.samusko.codereader.model.Repo;
import com.codereader.samusko.codereader.model.RepoContent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Bohdan on 27.11.2015.
 */
public class RetrofitManager {
    private static final String API_END_POINT = "https://api.github.com"; // Base URL

    public RetrofitManager() {

    }

    public static GitHubUser getAuthentication(String login, String password) {
        GitHubUser gitHubUser = null;

        Call<GitHubUser> call = getApi().getAuthentication(getLoginBuilder(login, password));

        try {
            gitHubUser = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gitHubUser;
    }

    public static ArrayList<Branch> getBranches(Context context, String repo_name) {
        ArrayList<Branch> branchesList = new ArrayList<>();

        String[] credentials  = Account.getCredentials(context); // getting credentials
        String login = credentials[0];
        String password = credentials[1];

        Call<List<Branch>> call = getApi().getBranches(getLoginBuilder(login, password), login, repo_name);

        try {
            branchesList.addAll(call.execute().body());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return branchesList;
    }

    public static ArrayList<Commit> getCommits(Context context, String repo_name, String branch) {
        ArrayList<Commit> commitsList = new ArrayList<>();

        String[] credentials  = Account.getCredentials(context); // getting credentials
        String login = credentials[0];
        String password = credentials[1];

        Call<List<Commit>> call = getApi().getCommits(getLoginBuilder(login, password), login, repo_name, branch);

        try {
            commitsList.addAll(call.execute().body());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return commitsList;
    }

    public static ArrayList<RepoContent> getRepoContent(Context context, String repo_name, String branch) {
        ArrayList<RepoContent> contentList = new ArrayList<>();

        String[] credentials  = Account.getCredentials(context); // getting credentials
        String login = credentials[0];
        String password = credentials[1];

        Call<List<RepoContent>> call = getApi().getRepoContent(getLoginBuilder(login, password), login, repo_name,branch);

        try {
            contentList.addAll(call.execute().body());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contentList;
    }

    public static ArrayList<RepoContent> getRepoContentByPath(Context context, String repo_name, String path, String branch) {
        ArrayList<RepoContent> contentList = new ArrayList<>();

        String[] credentials  = Account.getCredentials(context); // getting credentials
        String login = credentials[0];
        String password = credentials[1];

        Call<List<RepoContent>> call = getApi().getRepoContent(getLoginBuilder(login, password), login, repo_name, path, branch);

        try {
            contentList.addAll(call.execute().body());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contentList;
    }

    public static Repo getFullDataForRepo(String login, String password, String repo_name) {
        Repo repo = null;

        Call<Repo> call = getApi().getFullDataForRepo(getLoginBuilder(login, password), login, repo_name);

        try {
            repo = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return repo;
    }


    public static ArrayList<Repo> getRepositoriesForUser(String userLogin, String userPassword) {
        ArrayList<Repo> repos = new ArrayList<>();

        Call<List<Repo>> call = getApi().getRepositories(getLoginBuilder(userLogin, userPassword), userLogin);

        try {
            repos.addAll(call.execute().body());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return repos;
    }

    public static boolean checkCredentials(String login, String password) {
        try {
            Call<GitHubUser> call = getApi().getAuthentication(getLoginBuilder(login, password));
            GitHubUser user = call.execute().body();

            // trying to get user login for checking validation of credentials
            user.getLogin();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static IGitHubApi getApi() {
        //adapter fot retrofit with base URL
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IGitHubApi service = retrofit.create(IGitHubApi.class);

        return service;
    }

    private static String getLoginBuilder(String login, String password) {
        // Making loginBuilder
        byte[] loginBytes = (login + ":" + password).getBytes();

        StringBuilder loginBuilder = new StringBuilder()
                .append("Basic ")
                .append(Base64.encodeToString(loginBytes, Base64.DEFAULT));

        return loginBuilder.toString().trim();
    }
}
