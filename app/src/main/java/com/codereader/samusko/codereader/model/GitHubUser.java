package com.codereader.samusko.codereader.model;

import android.content.Context;
import android.graphics.Bitmap;

import com.codereader.samusko.codereader.api.RetrofitManager;
import com.codereader.samusko.codereader.interfaces.IGitHubUser;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Bohdan on 26.11.2015.
 */
public class GitHubUser implements IGitHubUser {
    ArrayList<Repo> allRepos = new ArrayList<>();
    ArrayList<Repo> forkedRepos = new ArrayList<>();

    Context context;

    private String login = "";
    private String name = "";
    private String company = "";
    private String avatar_url = "";
    private String html_url = "";
    private int followers;
    private int following;
    private String created_at;
    private Bitmap userAvatar = null;

    public GitHubUser(Context context){
        this.context = context;

        loadUserInfo();
        loadAllRepos();
        loadAvatar();
    }

    private ArrayList<Repo> loadAllRepos(){
        String[] credentials  = Account.getCredentials(context); // getting credentials
        String login = credentials[0];
        String password = credentials[1];

        allRepos.addAll(RetrofitManager.getRepositoriesForUser(login, password)); // setting login and password

        return allRepos;
    }

    private GitHubUser loadUserInfo(){
        GitHubUser user;

        String[] credentials  = Account.getCredentials(context); // getting credentials
        String login = credentials[0];
        String password = credentials[1];

        user = RetrofitManager.getAuthentication(login, password);

        this.login = user.getLogin();
        this.company = user.getCompany();
        this.avatar_url = user.getAvatar_url();
        this.html_url = user.getHtml_url();
        this.followers = user.getFollowers();
        this.following = user.getFollowing();
        this.created_at = user.getCreated_at();

        return user;
    }

    private Bitmap loadAvatar(){
        Bitmap bitmap = null;
        try {
            Bitmap avatar = Bitmap.createBitmap(Picasso.with(context).load(avatar_url).get());
            bitmap = Bitmap.createScaledBitmap(avatar, 100, 100, false);
        }catch (Exception e){
            e.printStackTrace();
        }
        this.userAvatar = bitmap;
        return bitmap;
    }

    public ArrayList<Repo> getUserOnlyRepos(){
        ArrayList<Repo> userRepos = new ArrayList<>();

        for(Repo repo : allRepos){
            if(repo.getFork().equals("false")){
                userRepos.add(repo);
            }
        }

        return  userRepos;
    }
    public ArrayList<Repo> getAllRepos(){
        return allRepos;
    }

    public ArrayList<Repo> loadForkedRepos(){
       // ArrayList<Repo> forkedRepos = new ArrayList<>();

        for(Repo repo : allRepos){
            if(repo.getFork().equals("true")){
                forkedRepos.add(repo);
            }
        }

        String[] credentials  = Account.getCredentials(context); // getting credentials
        String login = credentials[0];
        String password = credentials[1];

        for (int i = 0; i < forkedRepos.size(); i++){
            Repo repo =  RetrofitManager.getFullDataForRepo(login, password, forkedRepos.get(i).getName());
            forkedRepos.get(i).setParent(repo.getParent());
        }

       // loadFullDataForForkedRepos();
        return  forkedRepos;
    }

    public ArrayList<Repo> getForkedRepos(){
        return forkedRepos;
    }

    private void loadFullDataForForkedRepos(){

        String[] credentials  = Account.getCredentials(context); // getting credentials
        String login = credentials[0];
        String password = credentials[1];

        for (int i = 0; i < forkedRepos.size(); i++){
            forkedRepos.set(i, RetrofitManager.getFullDataForRepo(login, password, forkedRepos.get(i).getName()));
        }
    }

    public String getJoinedData(){

        Date dateOfJoined = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            dateOfJoined = dateFormat.parse(created_at);

            Calendar calendarDate = Calendar.getInstance();
            calendarDate.setTime(dateOfJoined);

            int intDay = calendarDate.get(Calendar.DAY_OF_MONTH);
            int intMonth = calendarDate.get(Calendar.MONTH) + 1;

            String day = (intDay > 9) ? String.valueOf(intDay) : "0" + String.valueOf(intDay);
            String month = (intMonth > 9) ? String.valueOf(intMonth) : "0" + String.valueOf(intMonth);
            String year = String.valueOf(calendarDate.get(Calendar.YEAR));


            return  "Joined on " + day + "." + month + "." + year;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Bitmap getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(Bitmap userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
