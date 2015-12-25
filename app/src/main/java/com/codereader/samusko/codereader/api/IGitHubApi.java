package com.codereader.samusko.codereader.api;

import com.codereader.samusko.codereader.model.Branch;
import com.codereader.samusko.codereader.model.Commit;
import com.codereader.samusko.codereader.model.GitHubUser;
import com.codereader.samusko.codereader.model.Repo;
import com.codereader.samusko.codereader.model.RepoContent;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Bohdan on 27.11.2015.
 */
public interface IGitHubApi {

    @GET("/user")
    Call<GitHubUser> getAuthentication(@Header("Authorization") String loginBuilder);

    @GET("/users/{user}/repos")
    Call<List<Repo>> getRepositories(@Header("Authorization") String loginBuilder,
                                     @Path("user") String user);

    @GET("/repos/{user}/{repo_name}")
    Call<Repo> getFullDataForRepo(@Header("Authorization") String loginBuilder, @Path("user") String user,
                                  @Path("repo_name") String repo_name);

    @GET("/repos/{owner}/{repo_name}/contents/{path}")
    Call<List<RepoContent>> getRepoContent(@Header("Authorization") String loginBuilder,
                                           @Path("owner") String owner,
                                           @Path("repo_name") String repo_name,
                                           @Path("path") String path,
                                           @Query("ref") String branch);

    @GET("/repos/{owner}/{repo_name}/contents")
    Call<List<RepoContent>> getRepoContent(@Header("Authorization") String loginBuilder,
                                           @Path("owner") String owner,
                                           @Path("repo_name") String repo_name,
                                           @Query("ref") String branch);

    @GET("/repos/{owner}/{repo_name}/commits")
    Call<List<Commit>> getCommits(@Header("Authorization") String loginBuilder,
                                  @Path("owner") String owner,
                                  @Path("repo_name") String repo_name,
                                  @Query("sha") String branch);

    @GET("/repos/{owner}/{repo_name}/branches")
    Call<List<Branch>> getBranches(@Header("Authorization") String loginBuilder,
                                   @Path("owner") String owner,
                                   @Path("repo_name") String repo_name);
}
