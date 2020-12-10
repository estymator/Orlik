package com.example.orlik.ui.games;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.orlik.R;
import com.example.orlik.data.model.Game;
import com.example.orlik.data.model.User;
import com.example.orlik.ui.Basic.BasicActivity;
import com.example.orlik.ui.main.MainActivity;
import com.example.orlik.ui.main.MainViewModel;
import com.example.orlik.ui.main.MainViewModelFactory;
import com.example.orlik.ui.organizeGames.OrganizeActivity;
import com.example.orlik.ui.settings.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class GamesActivity extends BasicActivity {
    private final String TAG="GamesActivity";
    BottomNavigationView bottomNavigationView;
    Spinner pitchSpinner, rangeSpinner;
    Button searchButton;
    GamesViewModel gamesViewModel;
    TextView addressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG,"OnCreate");
        gamesViewModel = ViewModelProviders.of(this, new GamesViewModelFactory())
                .get(GamesViewModel.class);
        gamesViewModel.getLocalization(this);
        setContentView(R.layout.activity_games);
        bottomNavigationView = findViewById(R.id.main_toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this); //get handlers from parent class
        bottomNavigationView.setOnNavigationItemReselectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_item_games);

        pitchSpinner = (Spinner) findViewById(R.id.games_pitch_spinner);
        gamesViewModel.setOrganizeSpinner(pitchSpinner,R.array.pitch_choose_array,this);

        rangeSpinner = (Spinner) findViewById(R.id.games_range_spinner);
        gamesViewModel.setOrganizeSpinner(rangeSpinner, R.array.distance_array, this);

        addressTextView = (TextView) findViewById(R.id.games_address_textView);

        searchButton = (Button) findViewById(R.id.games_search_button);
        //TODO: check if localization get correct, if not ask for manual data
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pitchChoose = pitchSpinner.getSelectedItem().toString();
                String rangeChoose = rangeSpinner.getSelectedItem().toString();
                gamesViewModel.searchGames(pitchChoose, rangeChoose, getApplicationContext());
            }

        });

        gamesViewModel.getAddress().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String address) {
                if(address==null){
                    //after logout
                }else
                {
                    addressTextView.setText(address);
                }
            }
        });
        gamesViewModel.getGamesList().observe(this, new Observer<ArrayList<Game>>() {
            @Override
            public void onChanged(ArrayList<Game> games) {
                Log.v(TAG,games.toString());
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "OnDestroy");
    }
}