package com.example.orlik.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.orlik.R;
import com.example.orlik.data.model.Friends;
import com.example.orlik.data.model.Session;
import com.example.orlik.data.model.User;
import com.example.orlik.ui.Basic.BasicActivity;
import com.example.orlik.ui.main.FriendsAdapter;
import com.example.orlik.ui.main.MainViewModel;
import com.example.orlik.ui.main.MainViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ProfileActivity extends BasicActivity {
    final static private String TAG="ProfileActivityTAG";
    private ProfileViewModel profileViewModel;
    BottomNavigationView bottomNavigationView;
    private User user;
    private TextView nameTextView, loginTextView, gameTextView, winRatioTextView, trustRateTextView, gamesAttendTextView, gamesOrganizedTextView, friendsTextView;
    private Button friendButton;
    private RecyclerView gamesAttendRecyclerView, gamesOrganizedRecyclerView, friendsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.v(TAG,"OnCreate");
        profileViewModel =  new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.setLoggedinUserLogin(new Session(this).getLogin());
        if(profileViewModel.getUser()==null){
            User user=(User) getIntent().getSerializableExtra("user");
            profileViewModel.setUser(user);
            profileViewModel.setFriend(getIntent().getBooleanExtra("friend",false));
        }

        bottomNavigationView = findViewById(R.id.main_toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemReselectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_item_blank);

        nameTextView = (TextView) findViewById(R.id.profile_name_TextView);
        loginTextView = (TextView) findViewById(R.id.profile_login_TextView);
        gameTextView = (TextView) findViewById(R.id.profile_game_TextView);
        winRatioTextView = (TextView) findViewById(R.id.profile_winRatio_TextView);
        trustRateTextView = (TextView) findViewById(R.id.profile_trustRate_TextView);
        gamesAttendTextView = (TextView) findViewById(R.id.profile_attendGames_textView);
        gamesOrganizedTextView = (TextView) findViewById(R.id.profile_organizedGames_textView);
        friendsTextView = (TextView) findViewById(R.id.profile_friends_textView);

        friendButton = (Button) findViewById(R.id.profile_deleteFriend_button);

        gamesAttendRecyclerView = (RecyclerView) findViewById(R.id.profile_attendGames_recycylerView);
        gamesOrganizedRecyclerView = (RecyclerView) findViewById(R.id.profile_organizedGames_recycylerView);
        friendsRecyclerView = (RecyclerView) findViewById(R.id.profile_friends_recycylerView);


        loadViewValues();

        profileViewModel.getFriends().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                friendsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                friendsRecyclerView.setAdapter(new FriendsAdapter(users));
            }
        });


        profileViewModel.getDeleteFriendsResult().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    profileViewModel.setFriend(false);
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        profileViewModel.getAddFriendsResult().observe(this, new Observer<Friends>() {
            @Override
            public void onChanged(Friends friends) {
                if(friends.getFirstLogin()==profileViewModel.getLoggedinUserLogin() && friends.getSecondLogin()==profileViewModel.getUser().getLogin()){
                    profileViewModel.setFriend(true);
                    finish();
                    startActivity(getIntent()); //TODO check if friends array list need to be erased
                }
            }
        });
    }


    private void loadViewValues(){
        User user = profileViewModel.getUser();
        if(user==null) return;

        float winRatio;
        if(user.getTotalGames()!=0){
            winRatio=(float)user.getWinGames()/user.getTotalGames();
        }else{
            winRatio=0;
        }

        nameTextView.setText(user.getName()+" "+user.getSurname());
        loginTextView.setText("Login: "+user.getLogin());
        gameTextView.setText("Liczba rozegranych gier: "+user.getTotalGames());
        winRatioTextView.setText("Współczynnik wygranych: "+winRatio);
        trustRateTextView.setText("Współczynnik zaufania: "+user.getTrustRate());

        if(profileViewModel.isFriend()){
            friendButton.setText(R.string.profile_deleteFriend_button);
            friendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    profileViewModel.deleteFriends();
                }
            });

            gamesOrganizedTextView.setVisibility(View.VISIBLE);
            gamesOrganizedRecyclerView.setVisibility(View.VISIBLE);
            gamesAttendTextView.setVisibility(View.VISIBLE);
            gamesAttendRecyclerView.setVisibility(View.VISIBLE);
            friendsTextView.setVisibility(View.VISIBLE);
            friendsRecyclerView.setVisibility(View.VISIBLE);

            profileViewModel.getFriendsOfUser();
            profileViewModel.getOrganisedGames();
            profileViewModel.getAttendGames();

        }else{
            friendButton.setText(R.string.profile_addFriend_button);
            friendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    profileViewModel.addFriends();

                }
            });
        }
    }
}