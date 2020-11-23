package com.example.orlik.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.orlik.Network.RetrofitServiceGenerator;
import com.example.orlik.R;
import com.example.orlik.data.model.Session;
import com.example.orlik.data.model.User;
import com.example.orlik.ui.Basic.BasicActivity;
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

public class MainActivity extends BasicActivity {
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
        super.onCreate(savedInstanceState);
        Log.v(TAG,"OnCreate");

        setContentView(R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this, new MainViewModelFactory())
                .get(MainViewModel.class);
        logoutButton = findViewById(R.id.main_logout_button);
        listUsersButton=findViewById(R.id.main_list_users_button);
        bottomNavigationView = findViewById(R.id.main_toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemReselectedListener(this);


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
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.v(TAG, "onResume");
        session= new Session(getApplicationContext());

    }



}