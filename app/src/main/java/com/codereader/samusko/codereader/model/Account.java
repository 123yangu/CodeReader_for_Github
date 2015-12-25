package com.codereader.samusko.codereader.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.codereader.samusko.codereader.interfaces.IAccount;

/**
 * Created by Bohdan on 26.11.2015.
 */
public class Account {
    private static final String FILE_CREDENTIONALS = "Credentials";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static String login = null;
    private static String password = null;

    public static void setCredentials(Context context, String user_login, String user_password) {
        login = user_login;
        password = user_password;
        saveCredentials(context, login, password);
    }

    public static String[] getCredentials(Context context) {
        String[] credentials = new String[2];

        if (login != null && password != null) {
            credentials[0] = login;
            credentials[1] = password;
        } else {
            credentials = readCredentials(context);
            login = credentials[0];
            password = credentials[1];
        }

        return credentials;
    }

    private static void saveCredentials(Context context, String login, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_CREDENTIONALS, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOGIN, login);
        editor.putString(PASSWORD, password);

        editor.apply();
    }

    private static String[] readCredentials(Context context) {
        String[] credentials = new String[2];

        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_CREDENTIONALS, context.MODE_PRIVATE);
        credentials[0] = sharedPreferences.getString(LOGIN, null);
        Log.i("Account", credentials[0]);
        credentials[1] = sharedPreferences.getString(PASSWORD, null);
        Log.i("Account", credentials[1]);

        return credentials;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        Account.login = login;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Account.password = password;
    }
}
