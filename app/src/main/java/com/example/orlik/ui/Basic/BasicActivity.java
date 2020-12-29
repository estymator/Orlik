package com.example.orlik.ui.Basic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.orlik.Network.RetrofitServiceGenerator;
import com.example.orlik.R;
import com.example.orlik.data.model.Session;
import com.example.orlik.ui.games.GamesActivity;
import com.example.orlik.ui.login.LoginActivity;
import com.example.orlik.ui.main.MainActivity;
import com.example.orlik.ui.organizeGames.OrganizeActivity;
import com.example.orlik.ui.settings.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BasicActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener{
    private final String TAG="BasicActivity";

    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "OnCreate");
        session = new Session(getApplicationContext());
        if (session.getCredentials() == null) {
            Log.v(TAG, "User niezalogowany");
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent,-1); //TODO : po przekierowaniu do loginu main acitivty dalej sie ładuje i wysyla zapytanie o znajomych
        } else if (session.getCredentials() != null) {
            RetrofitServiceGenerator.setCredentials(session.getCredentials().split(":")[0], session.getCredentials().split(":")[1]);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data)
    {
        super.onActivityResult(requestCode, responseCode, data);
        Log.v(TAG, "OnActivityResult()");


    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.v(TAG,"OnResume");
        session= new Session(getApplicationContext());

        if(session.getCredentials()==null)
        {
            Log.v(TAG, "User niezalogowany");
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if(session.getCredentials()!=null){
            RetrofitServiceGenerator.setCredentials(session.getCredentials().split(":")[0],session.getCredentials().split(":")[1]);
        }


    }

    @Override
    protected void onStart(){
        super.onStart();
        session= new Session(getApplicationContext());
        Log.v(TAG,"onStart");
        if(session.getCredentials()==null)
        {
            Log.v(TAG, "User niezalogowany");
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, 1);
        }else if(session.getCredentials()!=null){
            RetrofitServiceGenerator.setCredentials(session.getCredentials().split(":")[0],session.getCredentials().split(":")[1]);
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.menu_item_profile:
                if(this instanceof MainActivity) return true;
                Log.v(TAG,"main");
                intent= new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;

            case R.id.menu_item_settings:
                if(this instanceof SettingsActivity) return true;
                Log.v(TAG,"ustawienia");
                intent= new Intent(this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;

            case R.id.menu_item_organize:
                if(this instanceof OrganizeActivity) return true;
                Log.v(TAG,"stwórz");
                intent= new Intent(this, OrganizeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;

            case R.id.menu_item_games:
                if(this instanceof GamesActivity) return true;
                Log.v(TAG,"GRY");
                intent= new Intent(this, GamesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return false;
    }



    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.menu_item_profile:
                if(this instanceof MainActivity) break;
                Log.v(TAG,"main");
                intent= new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case R.id.menu_item_settings:
                if(this instanceof SettingsActivity) break;
                Log.v(TAG,"ustawienia");
                intent= new Intent(this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case R.id.menu_item_organize:
                if(this instanceof OrganizeActivity) break;
                Log.v(TAG,"stwórz");
                intent= new Intent(this, OrganizeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case R.id.menu_item_games:
                if(this instanceof GamesActivity) break;
                Log.v(TAG,"GRY");
                intent= new Intent(this, GamesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        Log.v(TAG,"Reselect");
    }

}