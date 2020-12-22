package com.example.orlik.ui.organizeGames;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.orlik.R;
import com.example.orlik.ui.Basic.BasicActivity;
import com.example.orlik.ui.organizeGames.fragments.OrganizeAddGameFragment;
import com.example.orlik.ui.organizeGames.fragments.OrganizeMenuFragment;
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