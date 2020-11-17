package com.example.orlik.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.orlik.Network.RetrofitServiceGenerator;
import com.example.orlik.R;
import com.example.orlik.data.model.Session;
import com.example.orlik.data.model.User;
import com.example.orlik.ui.games.GamesActivity;
import com.example.orlik.ui.login.LoginActivity;
import com.example.orlik.ui.organizeGames.OrganizeActivity;
import com.example.orlik.ui.settings.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Session session;
    private User loggedInUser;
    private static final String TAG="MainActivity";
    private MainViewModel mainViewModel;
    private boolean loginRedirectFlag=false; //check if already redirect to login
    BottomNavigationView bottomNavigationView;
    Button logoutButton;
    Button listUsersButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG,"OnCreate");
        super.onCreate(savedInstanceState);
        //if user is logged in
        session= new Session(this.getApplicationContext());
        if(session.getCredentials()==null)
        {
            loginRedirectFlag=true;
            Log.v(TAG, "User niezalogowany");
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else{
            RetrofitServiceGenerator.setCredentials(session.getCredentials().split(":")[0],session.getCredentials().split(":")[1]);
        }

        setContentView(R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this, new MainViewModelFactory())
                .get(MainViewModel.class);
        logoutButton = findViewById(R.id.main_logout_button);
        listUsersButton=findViewById(R.id.main_list_users_button);
        bottomNavigationView = findViewById(R.id.main_toolbar);


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainViewModel.logout();
            }
        });

        listUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.listUsers();
            }
        });

        mainViewModel.getLogoutResult().observe(this, new Observer<LogoutResult>() {
            @Override
            public void onChanged(@Nullable LogoutResult logoutResult) {
                Log.v(TAG,"logoutResult changed");
                if (logoutResult == null) {
                    return;
                }
                if (logoutResult.getResult() == "Wylogowano") {

                    Session sesja = new Session(getApplicationContext());
                    try{
                        sesja.logout();
                        finish();
                        startActivity(getIntent());
                    }catch (Exception e)
                    {
                        Log.v(TAG,"Błąd usuniecia danych z sesji");
                    }


                }else{
                    Toast.makeText(getApplicationContext(), logoutResult.getResult(), Toast.LENGTH_SHORT).show();
                }


            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.menu_item_games:
                        intent= new Intent(MainActivity.this, GamesActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        return true;

                    case R.id.menu_item_settings:
                        intent= new Intent(MainActivity.this, SettingsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        return true;

                    case R.id.menu_item_organize:
                        intent= new Intent(MainActivity.this, OrganizeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "OnPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "OnDestroy");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.v(TAG,"onStop");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.v(TAG,"onRestart");
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        Log.v(TAG, "On Start");
        session= new Session(getApplicationContext());

        if(session.getCredentials()==null)
        {
            loginRedirectFlag=true;
            Log.v(TAG, "User niezalogowany");
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if(session.getCredentials()!=null){
            RetrofitServiceGenerator.setCredentials(session.getCredentials().split(":")[0],session.getCredentials().split(":")[1]);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.v(TAG, "onResume");
        session= new Session(getApplicationContext());

        if(session.getCredentials()==null)
        {
            loginRedirectFlag=true;
            Log.v(TAG, "User niezalogowany");
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if(session.getCredentials()!=null){
            RetrofitServiceGenerator.setCredentials(session.getCredentials().split(":")[0],session.getCredentials().split(":")[1]);
        }
    }



}