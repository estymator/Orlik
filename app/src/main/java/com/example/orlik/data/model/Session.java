package com.example.orlik.data.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;


public class Session {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String UserSessionPreferencies = "com.example.Orlik.userInformation";
    public static final String PasswordKey = "passwordKey";
    public static final String UsernameKey = "usernameKey";
    public static final String userKey = "userKey";

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
        editor.putString(UsernameKey,username);
        editor.putString(PasswordKey,password);
        editor.commit();
    }
    public void setUser(User u){
        String userChain=u.toString();
        editor.putString(userKey,userChain);
        editor.commit();
    }

    public User getUser(){
        String userString=sharedPreferences.getString(userKey,null);
        if(userString!=null){
            Gson gson = new Gson();
            User u = gson.fromJson(userString, User.class);
            return u;
        }else{
            return null;
        }
    }

    public void logout()
    {
        editor.clear();
        editor.commit();
    }

    /**
     *
     * @return String  - username:password
     */
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
