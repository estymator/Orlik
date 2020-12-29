package com.example.orlik.ui.profile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.orlik.R;
import com.example.orlik.data.adapters.GamesResultAdapter;
import com.example.orlik.data.model.Friends;
import com.example.orlik.data.model.Game;
import com.example.orlik.data.model.Session;
import com.example.orlik.data.model.User;
import com.example.orlik.data.model.dto.GameDTO;
import com.example.orlik.ui.Basic.BasicActivity;
import com.example.orlik.data.adapters.FriendsAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ProfileActivity extends BasicActivity {
    //TODO add statistic
    final static private String TAG="ProfileActivityTAG";
    private ProfileViewModel profileViewModel;
    BottomNavigationView bottomNavigationView;
    private User user;
    private TextView nameTextView, loginTextView, gameTextView, winRatioTextView, trustRateTextView, gamesAttendTextView, gamesOrganizedTextView, friendsTextView;
    private Button friendButton;
    private RecyclerView gamesAttendRecyclerView, gamesOrganizedRecyclerView, friendsRecyclerView;
    private RelativeLayout friendsRelativeLayout, organizedGamesRelativeLayout, attendGamesRelativeLayout;

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

        friendButton = (Button) findViewById(R.id.profile_turnFriend_button);

        gamesAttendRecyclerView = (RecyclerView) findViewById(R.id.profile_attendGames_recycylerView);
        gamesOrganizedRecyclerView = (RecyclerView) findViewById(R.id.profile_organizedGames_recycylerView);
        friendsRecyclerView = (RecyclerView) findViewById(R.id.profile_friends_recycylerView);

        friendsRelativeLayout = (RelativeLayout) findViewById(R.id.profile_friends_relativeLayout);
        organizedGamesRelativeLayout = (RelativeLayout) findViewById(R.id.profile_organizedGames_relativeLayout);
        attendGamesRelativeLayout = (RelativeLayout) findViewById(R.id.profile_attendGames_relativeLayout);


        loadViewValues();

        profileViewModel.getFriends().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                friendsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                friendsRecyclerView.setAdapter(new FriendsAdapter(users, true));
            }
        });


        profileViewModel.getDeleteFriendsResult().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    finish();
                    Intent intent=getIntent();
                    intent.putExtra("friend",false);
                    startActivity(intent);
                }
            }
        });

        profileViewModel.getAddFriendsResult().observe(this, new Observer<Friends>() {
            @Override
            public void onChanged(Friends friends) {
                Log.v(TAG,"Zmiana Friends"+friends.toString());
                if(friends.getFirstLogin().equals(profileViewModel.getLoggedinUserLogin()) && friends.getSecondLogin().equals(profileViewModel.getUser().getLogin())){
                    finish();
                    Intent intent=getIntent();
                    intent.putExtra("friend",true);
                    startActivity(intent); //TODO check if friends array list need to be erased
                }

            }
        });

        profileViewModel.getOrganisedGames().observe(this, new Observer<ArrayList<GameDTO>>() {
            @Override
            public void onChanged(ArrayList<GameDTO> games) {
                if(games.size()>0){
                    gamesOrganizedRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    gamesOrganizedRecyclerView.setAdapter(new GamesResultAdapter(games));
                }
            }
        });

        profileViewModel.getAttendGames().observe(this, new Observer<ArrayList<GameDTO>>() {
            @Override
            public void onChanged(ArrayList<GameDTO> games) {
                if(games.size()>0){
                    gamesAttendRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    gamesAttendRecyclerView.setAdapter(new GamesResultAdapter(games));
                }
            }
        });
    }


    private void loadViewValues(){
        Log.v(TAG, "loadViewValues");
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
            Log.v(TAG, "loadViewValues - Friends");
            friendButton.setText(R.string.profile_deleteFriend_button);
            friendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    profileViewModel.deleteFriends();
                }
            });

            gamesOrganizedTextView.setVisibility(View.VISIBLE);
            organizedGamesRelativeLayout.setVisibility(View.VISIBLE);
            gamesAttendTextView.setVisibility(View.VISIBLE);
            attendGamesRelativeLayout.setVisibility(View.VISIBLE);
            friendsTextView.setVisibility(View.VISIBLE);
            friendsRelativeLayout.setVisibility(View.VISIBLE);

            profileViewModel.getFriendsOfUser();
            profileViewModel.getUserOrganisedGames();
            profileViewModel.getUserAttendGames();

        }else{
            Log.v(TAG, "loadViewValues-not Friends");
            gamesOrganizedTextView.setVisibility(View.INVISIBLE);
            organizedGamesRelativeLayout.setVisibility(View.INVISIBLE);
            gamesAttendTextView.setVisibility(View.INVISIBLE);
            attendGamesRelativeLayout.setVisibility(View.INVISIBLE);
            friendsTextView.setVisibility(View.INVISIBLE);
            friendsRelativeLayout.setVisibility(View.INVISIBLE);

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