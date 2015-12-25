package com.codereader.samusko.codereader.model;

/**
 * Created by Bohdan on 14.12.2015.
 */
public class Branch {
    String name;
    Commit commit;

    class Commit{
        String sha;
        String url; // for retrieving all commits for the branch
    }

    public String getDescription(){
        return "UserName updated 5 hours ago";
    }

    public String getName() {
        return name;
    }
}
