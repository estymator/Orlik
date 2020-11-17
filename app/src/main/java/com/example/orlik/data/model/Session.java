package com.example.orlik.data.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;


public class Session {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String UserSessionPreferencies = "com.example.Orlik.userInformation";
    public static final String PasswordKey = "passwordKey";
    public static final String UsernameKey = "usernameKey";

    public Session(Context context)
    {
        sharedPreferences = context.getSharedPreferences(UserSessionPreferencies, MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public Session(Context context, String[] tab)
    {
        sharedPreferences = context.getSharedPreferences(UserSessionPreferencies, MODE_PRIVATE);
        editor=sharedPreferences.edit();
        setCredentials(tab[0],tab[1]);
    }
    public void setCredentials(String username, String password)
    {
        Log.v("Zapisuje dane", " Session ");
        editor.putString(UsernameKey,username);
        editor.putString(PasswordKey,password);
        editor.commit();
    }

    public void logout()
    {
        editor.clear();
        editor.commit();
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

    public String getLogin()
    {
        String username=sharedPreferences.getString(UsernameKey,null);

        if(username==null)
        {
            return null;
        }else{
            return username;
        }
    }

}
