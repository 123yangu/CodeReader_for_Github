package com.codereader.samusko.codereader.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.codereader.samusko.codereader.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Bohdan on 11.12.2015.
 */
public class Commit {
    String sha;
    CommitRepo commit;
    Author author;
    Bitmap authorAvatar;

    class CommitRepo {
        String message;
        CommitAuthor author;

        class CommitAuthor {
            String name;
            String date;
        }
    }

    class Author{
        String login;
        String avatar_url;
    }

    public String getSha() {
        return sha;
    }


    public String getCommitDate(){

        Date commitDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            commitDate = dateFormat.parse(commit.author.date);

            Calendar calendarCommitDate = Calendar.getInstance();
            calendarCommitDate.setTime(commitDate);

            int intDay = calendarCommitDate.get(Calendar.DAY_OF_MONTH);
            int intMonth = calendarCommitDate.get(Calendar.MONTH) + 1;

            String day = (intDay>9) ? String.valueOf(intDay) : "0"+String.valueOf(intDay);
            String month  = (intMonth>9) ? String.valueOf(intMonth) : "0"+String.valueOf(intMonth);
            String year  = String.valueOf(calendarCommitDate.get(Calendar.YEAR));

            return day + "." + month + "." + year;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getAuthorName(){
        if(commit.author.name!=null){
            return commit.author.name;
        }else {
            return author.login;
        }
    }
    // returns commit message
    public String getCommitMessage(){
        String message = commit.message;
        return message;
    }

    public Bitmap loadAuthorAvatar(Context context) {
        if (author != null) {
            try {
                Bitmap avatar = Bitmap.createBitmap(Picasso.with(context).load(getAvatarUrl()).get());
                authorAvatar = Bitmap.createScaledBitmap(avatar, 100, 100, false);
                return authorAvatar;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            authorAvatar = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_default_commit);
        }

        return authorAvatar;
    }

    private String getAvatarUrl(){
        return author.avatar_url;
    }

    public Bitmap getAuthorAvatar() {
        return authorAvatar;
    }
}
