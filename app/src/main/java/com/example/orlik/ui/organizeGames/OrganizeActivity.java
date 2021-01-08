package com.example.orlik.ui.organizeGames;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.orlik.R;
import com.example.orlik.data.model.User;
import com.example.orlik.ui.Basic.BasicActivity;
import com.example.orlik.ui.organizeGames.fragments.OrganizeAddGameFragment;
import com.example.orlik.ui.organizeGames.fragments.OrganizeAddPitchFragment;
import com.example.orlik.ui.organizeGames.fragments.OrganizeInvalidPitchListFragment;
import com.example.orlik.ui.organizeGames.fragments.OrganizeMenuFragment;
import com.example.orlik.ui.organizeGames.fragments.OrganizePitchListFragment;
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
        int pitchId =(int) getIntent().getIntExtra("pitch", 0);

        setContentView(R.layout.activity_organize);
        bottomNavigationView = findViewById(R.id.main_toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);// get handlers from parent app
        bottomNavigationView.setOnNavigationItemReselectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_item_add); //TODO check if this line is neccessary
        checkAdmin(bottomNavigationView);

        if(savedInstanceState ==null){
            if(pitchId>0){
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .add(R.id.organize_fragment_container_view, OrganizeAddGameFragment.class , null)
                        .commit();
            }else{
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.organize_fragment_container_view, OrganizeMenuFragment.class , null)
                        .commit();
            }


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
                    case "addPitch":
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .addToBackStack(null)
                                .replace(R.id.organize_fragment_container_view, OrganizeAddPitchFragment.class , null)
                                .commit();
                        break;
                    case "pitch":
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .addToBackStack(null)
                                .replace(R.id.organize_fragment_container_view, OrganizePitchListFragment.class , null)
                                .commit();
                        break;
                case "invalidPitch":
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .replace(R.id.organize_fragment_container_view, OrganizeInvalidPitchListFragment.class , null)
                            .commit();
                    break;
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