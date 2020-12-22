package com.example.orlik.ui.organizeGames;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import com.example.orlik.ui.games.SearchGameFragment;
import com.example.orlik.ui.main.MainActivity;
import com.example.orlik.ui.main.MainViewModel;
import com.example.orlik.ui.main.MainViewModelFactory;
import com.example.orlik.ui.settings.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrganizeActivity extends BasicActivity {
    BottomNavigationView bottomNavigationView;
    final private String TAG="OrganizeActivity";
    private OrganizeViewModel organizeViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG,"OnCreate");
        super.onCreate(savedInstanceState);
        organizeViewModel= new ViewModelProvider(this, new OrganizeViewModelFactory())
                .get(OrganizeViewModel.class);
        organizeViewModel.setContext(this);

        setContentView(R.layout.activity_organize);
        bottomNavigationView = findViewById(R.id.main_toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);// get handlers from parent app
        bottomNavigationView.setOnNavigationItemReselectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_item_organize);

        if(savedInstanceState ==null){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.organize_fragment_container_view, OrganizeMenuFragment.class , null)
                    .commit();

        }

        organizeViewModel.getFragmentNavigator().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s){
                    case "game":
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .addToBackStack(null)
                                .replace(R.id.organize_fragment_container_view, OrganizeAddGameFragment.class , null)
                                .commit();
                        break;
                    case "Pitch":
                        Log.v(TAG ,"Pitch");
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