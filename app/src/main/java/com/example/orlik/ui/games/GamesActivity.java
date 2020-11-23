package com.example.orlik.ui.games;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.orlik.R;
import com.example.orlik.ui.Basic.BasicActivity;
import com.example.orlik.ui.main.MainActivity;
import com.example.orlik.ui.organizeGames.OrganizeActivity;
import com.example.orlik.ui.settings.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GamesActivity extends BasicActivity {
    private final String TAG="GamesActivity";
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG,"OnCreate");
        setContentView(R.layout.activity_games);
        bottomNavigationView = findViewById(R.id.main_toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this); //get handlers from parent class
        bottomNavigationView.setOnNavigationItemReselectedListener(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "OnDestroy");
    }
}