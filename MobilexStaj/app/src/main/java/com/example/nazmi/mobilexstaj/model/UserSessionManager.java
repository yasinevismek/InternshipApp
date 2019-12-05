package com.example.nazmi.mobilexstaj.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.nazmi.mobilexstaj.LoginScreenActivity;

import okhttp3.internal.cache.DiskLruCache;

public class UserSessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREFER_NAME = "MobileXPreferences";

    private static final String IS_USER_LOGIN  = "IsUserLoggedIn";

    private static final String KEY_NAME = "name";

    private static final String KEY_EMAIL = "email";

    public UserSessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createUserLoginSession(String name,String email){

        editor.putString(KEY_NAME,name);
        editor.putString(KEY_EMAIL,email);
        editor.commit();
    }



    public void logoutUser(){
        editor.clear();
        editor.commit();
    }

    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN,false);
    }
}
