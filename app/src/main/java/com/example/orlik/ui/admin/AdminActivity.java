package com.example.orlik.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.orlik.R;
import com.example.orlik.data.model.Pitch;
import com.example.orlik.data.model.User;
import com.example.orlik.ui.Basic.BasicActivity;
import com.example.orlik.ui.admin.fragments.AdminPitchListFragment;
import com.example.orlik.ui.admin.fragments.AdminUserListFragment;
import com.example.orlik.ui.match.MatchViewModel;
import com.example.orlik.ui.match.MatchViewModelProvider;
import com.example.orlik.ui.organizeGames.fragments.OrganizeAddGameFragment;
import com.example.orlik.ui.organizeGames.fragments.OrganizeAddPitchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class AdminActivity extends BasicActivity {
    private final static String TAG = "AdminActivityTAG";
    private AdminViewModel adminViewModel;
    private BottomNavigationView bottomNavigationView;
    private EditText searchUserEditText, searchPitchEditText;
    private Button searchUserButton, searchPitchButton;
    private FragmentContainerView adminFragmentContainerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        this.adminViewModel = new ViewModelProvider(this, new AdminViewModelProvider()).get(AdminViewModel.class);

        bottomNavigationView = findViewById(R.id.main_toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);// get handlers from parent app
        bottomNavigationView.setOnNavigationItemReselectedListener(this);
        checkAdmin(bottomNavigationView);

        searchUserEditText = (EditText) findViewById(R.id.admin_searchUsers_editText);
        searchPitchEditText = (EditText) findViewById(R.id.admin_searchPitch_editText);

        searchUserButton = (Button) findViewById(R.id.admin_searchUsers_button);
        searchUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchUserEditText.getText().toString().length()>0){
                    adminViewModel.searchUsers(searchUserEditText.getText().toString());
                }
            }
        });

        searchPitchButton = (Button) findViewById(R.id.admin_searchPitch_button);
        searchPitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchPitchEditText.getText().toString().length()>0){
                    adminViewModel.searchPitch(searchPitchEditText.getText().toString());
                }
            }
        });

        adminViewModel.getFindPitchResult().observe(this, new Observer<ArrayList<Pitch>>() {
            @Override
            public void onChanged(ArrayList<Pitch> pitches) {
                if(pitches.size()>0)
                {
                    adminViewModel.getFragmentNavigator().setValue("findPitch");
                }
            }
        });

        adminViewModel.getFindUserResult().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> userArrayList) {
                if(userArrayList.size()>0){
                    adminViewModel.getFragmentNavigator().setValue("findUser");
                }
            }
        });

        adminViewModel.getFragmentNavigator().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                switch (s) {
                    case "findPitch":
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .addToBackStack(null)
                                .replace(R.id.admin_fragment_container_view, AdminPitchListFragment.class, null)
                                .commit();
                        break;
                    case "findUser":
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .addToBackStack(null)
                                .replace(R.id.admin_fragment_container_view, AdminUserListFragment.class, null) //TODO wyglad
                                .commit();
                        break;
                    default:
                        break;
                }
            }
        });

    }
}