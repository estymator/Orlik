package com.example.orlik.ui.games;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.orlik.R;
import com.example.orlik.data.model.Game;
import com.example.orlik.ui.Basic.BasicActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class GamesActivity extends BasicActivity {
    private final String TAG="GamesActivity";
    BottomNavigationView bottomNavigationView;
    GamesViewModel gamesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG,"OnCreate");
        gamesViewModel = new ViewModelProvider(this, new GamesViewModelFactory())
                .get(GamesViewModel.class);
        setContentView(R.layout.activity_games);

        if(savedInstanceState ==null){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.games_fragment_container_view,SearchGameFragment.class , null)
                    .commit();
        }
        gamesViewModel.getLocalization(this);

        bottomNavigationView = findViewById(R.id.main_toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this); //get handlers from parent class
        bottomNavigationView.setOnNavigationItemReselectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_item_games);

        gamesViewModel.getGamesList().observe(this, new Observer<ArrayList<Game>>() {
            @Override
            public void onChanged(ArrayList<Game> games) {
                if(games.size()==0){
                    Toast.makeText(getApplicationContext(), "Brak meczy w okolicy", Toast.LENGTH_SHORT).show();
                }else{
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .add(R.id.games_fragment_container_view, GamesResultFragment.class , null)
                            .commit();
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "OnDestroy");
    }
}