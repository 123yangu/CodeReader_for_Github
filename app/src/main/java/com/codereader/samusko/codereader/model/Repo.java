package com.codereader.samusko.codereader.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Bohdan on 26.11.2015.
 */
public class Repo {

    private String name = "";
    private String description = "";
    private String language = "";
    private int forks;
    private int stargazers_count;
    private String created_at = "";
    private String updated_at = "";
    private String pushed_at = "";
    private String fork = "";
    private String url = "";

    private Parent parent; // root parent for forked repo


    public String getLastUpdate() {
        String lastUpdate = null;

        Date dateOfUpdate = null;
        SimpleDateFormat updateDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            dateOfUpdate = updateDateFormat.parse(pushed_at);

            Calendar calendarUpdate = Calendar.getInstance();
            calendarUpdate.setTime(dateOfUpdate);

            int intDay = calendarUpdate.get(Calendar.DAY_OF_MONTH);
            int intMonth = calendarUpdate.get(Calendar.MONTH) + 1;

            String day = (intDay > 9) ? String.valueOf(intDay) : "0" + String.valueOf(intDay);
            String month = (intMonth > 9) ? String.valueOf(intMonth) : "0" + String.valueOf(intMonth);
            String year = String.valueOf(calendarUpdate.get(Calendar.YEAR));

            lastUpdate = "Updated on " + day + "." + month + "." + year;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*long today = Calendar.getInstance().getTimeInMillis();
        calendarLastUpdate = Calendar.getInstance();
        calendarLastUpdate.setTime(dateOfUpdate);

        long update = calendarLastUpdate.getTimeInMillis();


        long lastUpdateMilliseconds = dateOfUpdate.getTime();
        long difference = today - update;


        int days = (int) difference / (24 * 60 * 60 * 1000);
        int hours = (int) difference / (60 * 60 * 1000);
        int minutes = (int) difference / (60 * 1000);

        Log.i("ParsingDate", "Days: " + days + ", Hours: " + hours + ", Minutes: " + minutes);

        if (days != 0) {
            return "Updated " + days + " days ago";
        } else if (hours != 0) {
            return "Updated " + hours + " hours ago";
        } else if (minutes != 0) {
            return "Updates " + minutes + " minutes ago";
        }
*/

        return lastUpdate;
    }

    public String getForkedFrom() {
        return "forked from " + parent.getFull_name();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getForks() {
        if (parent != null && fork.equals("true") ) {
            return parent.getForks_count();
        } else
            return forks;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getPushed_at() {
        return pushed_at;
    }

    public void setPushed_at(String pushed_at) {
        this.pushed_at = pushed_at;
    }

    public String getFork() {
        return fork;
    }

    public void setFork(String fork) {
        this.fork = fork;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    class Parent {
        private String name;
        private String full_name;
        private int forks_count = 0;

        public int getForks_count() {
            return forks_count;
        }

        public String getName() {
            return name;
        }

        public String getFull_name() {
            return full_name;
        }
    }

}
