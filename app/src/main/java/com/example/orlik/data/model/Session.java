package com.example.orlik.data.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static android.content.Context.MODE_PRIVATE;


public class Session {
    private SharedPreferences sharedPreferences;

    public static final String UserSessionPreferencies = "userInformation";
    public static final String PasswordKey = "passwordKey";
    public static final String UsernameKey = "usernameKey";

    public Session(Context context)
    {
        sharedPreferences = context.getSharedPreferences(UserSessionPreferencies, MODE_PRIVATE);
    }
    public void setCredentials(String username, String password)
    {
        sharedPreferences.edit().putString(UsernameKey,username);
        sharedPreferences.edit().putString(PasswordKey,password);
    }

    public void logout()
    {
        sharedPreferences.edit().clear();
        sharedPreferences.edit().commit();
    }
    public String getCredentials()
    {
        String username=sharedPreferences.getString(UsernameKey,null);
        String password=sharedPreferences.getString(PasswordKey,null);
        if(username==null||password==null)
        {
            return null;
        }else{
            return username+":"+password;
        }
    }

}
