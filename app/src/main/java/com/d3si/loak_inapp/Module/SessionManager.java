package com.d3si.loak_inapp.Module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager
{
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LoakIn";
    public static final String KEY_ID = "ID";
    public static final String KEY_JENIS_USER = "JENIS_USER";
    public static final String KEY_EMAIL = "EMAIL";
    public static final String KEY_NAME = "NAMA";

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String id, String JU, String email, String nama) {

        editor.putString(KEY_ID, id);
        editor.putString(KEY_JENIS_USER, JU);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NAME, nama);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {

        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_JENIS_USER, pref.getString(KEY_JENIS_USER, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        return user;
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }

}
