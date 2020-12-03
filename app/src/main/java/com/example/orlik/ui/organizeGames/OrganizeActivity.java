package com.example.orlik.ui.organizeGames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.orlik.R;
import com.example.orlik.data.location.LocationGetter;
import com.example.orlik.ui.Basic.BasicActivity;
import com.example.orlik.ui.games.GamesActivity;
import com.example.orlik.ui.main.MainActivity;
import com.example.orlik.ui.main.MainViewModel;
import com.example.orlik.ui.main.MainViewModelFactory;
import com.example.orlik.ui.settings.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrganizeActivity extends BasicActivity {
    BottomNavigationView bottomNavigationView;
    final private String TAG="OrganizeActivity";
    private OrganizeViewModel organizeViewModel;
    private Spinner maxPlayersSpinner, minPlayersSpinner, visibilitySpinner;
    private Button localizationButton;
    private LocationGetter locationGetter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG,"OnCreate");
        super.onCreate(savedInstanceState);
        organizeViewModel= ViewModelProviders.of(this, new OrganizeViewModelFactory())
                .get(OrganizeViewModel.class);
        organizeViewModel.setContext(this);

        setContentView(R.layout.activity_organize);
        bottomNavigationView = findViewById(R.id.main_toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);// get handlers from parent app
        bottomNavigationView.setOnNavigationItemReselectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_item_organize);

        maxPlayersSpinner=(Spinner) findViewById(R.id.organize_max_players_spinner);
        organizeViewModel.setOrganizeSpinner(maxPlayersSpinner,R.array.players_number_array);

        minPlayersSpinner=(Spinner) findViewById(R.id.organize_min_players_spinner);
        organizeViewModel.setOrganizeSpinner(minPlayersSpinner,R.array.players_number_array);

        visibilitySpinner=(Spinner) findViewById(R.id.organize_visibility_spinner);
        organizeViewModel.setOrganizeSpinner(visibilitySpinner,R.array.visibility_array);

        localizationButton=(Button) findViewById(R.id.organize_localization_button);




        localizationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Log.v(TAG, locationGetter.getLat()+" "+locationGetter.getLon());

            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "OnDestroy");
    }
}