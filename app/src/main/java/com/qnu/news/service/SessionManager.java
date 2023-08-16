package com.qnu.news.service;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "MyPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // Method to save login details
    public void saveLoginDetails(String username, String password) {
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    // Method to check if the user is logged in
    public boolean isLoggedIn() {
        return pref.contains(KEY_USERNAME) && pref.contains(KEY_PASSWORD);
    }

    // Method to get logged in username
    public String getLoggedInUsername() {
        return pref.getString(KEY_USERNAME, "");
    }

    // Method to get logged in password
    public String getLoggedInPassword() {
        return pref.getString(KEY_PASSWORD, "");
    }

    // Method to perform logout
    public void logout() {
        editor.clear();
        editor.apply();
    }
}



