package com.example.orlik.ui.stats;

import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.orlik.R;
import com.example.orlik.data.model.Session;
import com.example.orlik.ui.Basic.BasicActivity;
import com.example.orlik.ui.organizeGames.fragments.OrganizeMenuFragment;
import com.example.orlik.ui.stats.fragments.StatsListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends BasicActivity {
    private final String TAG="SettingsActivity";
    BottomNavigationView bottomNavigationView;
    private StatsViewModel statsViewModel;
    FragmentContainerView fragmentContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG,"OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        statsViewModel=new ViewModelProvider(this, new StatsViewModelFactory()).get(StatsViewModel.class);
        statsViewModel.setUser(new Session(this));

        fragmentContainerView = findViewById(R.id.stats_fragment_container_view);
        bottomNavigationView = findViewById(R.id.main_toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this); //get select handler from parent class
        bottomNavigationView.setOnNavigationItemReselectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_item_stats);
        checkAdmin(bottomNavigationView);

        if(savedInstanceState ==null){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.stats_fragment_container_view, StatsListFragment.class , null)
                    .commit();
        }

        statsViewModel.getFragmentNavigator().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "OnDestroy");
    }
}