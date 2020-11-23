package com.example.orlik.ui.organizeGames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.orlik.R;
import com.example.orlik.ui.Basic.BasicActivity;
import com.example.orlik.ui.games.GamesActivity;
import com.example.orlik.ui.main.MainActivity;
import com.example.orlik.ui.settings.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrganizeActivity extends BasicActivity {
    BottomNavigationView bottomNavigationView;
    final private String TAG="OrganizeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG,"OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organize);
        bottomNavigationView = findViewById(R.id.main_toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);// get handlers from parent app
        bottomNavigationView.setOnNavigationItemReselectedListener(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "OnDestroy");
    }
}