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
import android.widget.Toast;

import com.example.orlik.R;
import com.example.orlik.data.adapters.GamesResultAdapter;
import com.example.orlik.data.model.Friends;
import com.example.orlik.data.model.Game;
import com.example.orlik.data.model.Notification;
import com.example.orlik.data.model.Session;
import com.example.orlik.data.model.User;
import com.example.orlik.data.model.dto.GameDTO;
import com.example.orlik.ui.Basic.BasicActivity;
import com.example.orlik.data.adapters.FriendsAdapter;
import com.example.orlik.ui.main.MainActivity;
import com.example.orlik.ui.organizeGames.OrganizeActivity;
import com.example.orlik.ui.profile.dialogs.BlockUserDialog;
import com.example.orlik.ui.profile.dialogs.RemoveUserDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ProfileActivity extends BasicActivity {
    //TODO add statistic
    final static private String TAG="ProfileActivityTAG";
    private ProfileViewModel profileViewModel;
    BottomNavigationView bottomNavigationView;
    private User user;
    private TextView nameTextView, loginTextView, gameTextView, winRatioTextView, trustRateTextView, gamesAttendTextView, gamesOrganizedTextView, friendsTextView;
    private Button friendButton, adminBlockButton, adminDeleteButton;
    private RecyclerView gamesAttendRecyclerView, gamesOrganizedRecyclerView, friendsRecyclerView;
    private RelativeLayout friendsRelativeLayout, organizedGamesRelativeLayout, attendGamesRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.v(TAG,"OnCreate");
        profileViewModel =  new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.setLoggedinUserLogin(new Session(this).getLogin());
        profileViewModel.setSession(this);
        if(profileViewModel.getUser()==null){
            User user=(User) getIntent().getSerializableExtra("user");
            profileViewModel.setUser(user);
            profileViewModel.setFriend(getIntent().getBooleanExtra("friend",false));
            profileViewModel.checkFriendInvitation(user.getLogin());
        }


        bottomNavigationView = findViewById(R.id.main_toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemReselectedListener(this);
        checkAdmin(bottomNavigationView);

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

        adminBlockButton = (Button) findViewById(R.id.profile_admin_blockUser_Button);
        adminDeleteButton = (Button) findViewById(R.id.profile_admin_deleteUser_Button);
        if(profileViewModel.isAdmin()){
            adminDeleteButton.setVisibility(View.VISIBLE);
            if(profileViewModel.getUser().isValid()){
                adminBlockButton.setVisibility(View.VISIBLE);
            }else{
                adminBlockButton.setText("Odblokuj");
                adminBlockButton.setVisibility(View.VISIBLE);
            }

            adminDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RemoveUserDialog removeUserDialog = new RemoveUserDialog();
                    removeUserDialog.show(getSupportFragmentManager(), "removeUserDialog");
                }
            });

            adminBlockButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BlockUserDialog blockUserDialog = new BlockUserDialog();
                    blockUserDialog.show(getSupportFragmentManager(), "blockUserDialog");
                }
            });
        }


        loadViewValues();

        profileViewModel.getFindNotificationResult().observe(this, new Observer<Notification>() {
            @Override
            public void onChanged(Notification notification) {
                if(notification!=null){
                    Log.v(TAG, notification.toString());
                    if(notification.getSourceLogin().equals(profileViewModel.getLoggedinUserLogin()) && notification.getDestinationLogin().equals(profileViewModel.getUser().getLogin())){
                        friendButton.setText("Wysłano prośbę");
                        friendButton.setEnabled(false);
                    }else if(notification.getDestinationLogin().equals(profileViewModel.getLoggedinUserLogin()) && notification.getSourceLogin().equals(profileViewModel.getUser().getLogin())){
                        friendButton.setText("Zaakceptuj zaproszenie");
                        friendButton.setEnabled(true);
                        profileViewModel.setSendIvitation(true);
                        loadViewValues();
                    }
                }
            }
        });

        profileViewModel.getConfirmFriendsResult().observe(this, new Observer<Friends>() {
            @Override
            public void onChanged(Friends friends) {
                if(friends!=null){
                    if(friends.getFirstLogin().equals(profileViewModel.getUser().getLogin())||friends.getSecondLogin().equals(profileViewModel.getUser().getLogin())){
                        profileViewModel.setFriend(true);
                        loadViewValues();
                    }
                }
            }
        });

        profileViewModel.getFriends().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                friendsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                friendsRecyclerView.setAdapter(new FriendsAdapter(users, false));
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

        profileViewModel.getAddFriendsResult().observe(this, new Observer<Notification>() {
            @Override
            public void onChanged(Notification notification) {
                if(notification!=null){
                    if(notification.getSourceLogin().equals(profileViewModel.getLoggedinUserLogin()) && notification.getDestinationLogin().equals(profileViewModel.getUser().getLogin())){
                        friendButton.setText("Wysłano prośbę");
                    }
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

        profileViewModel.getDeleteUserResult().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast ToastMessage = Toast.makeText(getApplicationContext(),"Profil Usuniety",Toast.LENGTH_SHORT);
                View toastView = ToastMessage.getView();
                toastView.setBackgroundColor(getResources().getColor(R.color.toastBackground));
                ToastMessage.show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        profileViewModel.getUnbanUserResult().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User userResponse) {
                if(userResponse!=null){
                    Log.v(TAG, userResponse.toString());
                    if(userResponse.isValid()){
                        profileViewModel.setUser(userResponse);
                        Toast ToastMessage = Toast.makeText(getApplicationContext(),"Profil Odblokowany",Toast.LENGTH_SHORT);
                        View toastView = ToastMessage.getView();
                        toastView.setBackgroundColor(getResources().getColor(R.color.toastBackground));
                        ToastMessage.show();
                        loadViewValues();
                    }else{
                        Toast ToastMessage = Toast.makeText(getApplicationContext(), "Brak Uprawnień", Toast.LENGTH_SHORT);
                        View toastView = ToastMessage.getView();
                        toastView.setBackgroundColor(getResources().getColor(R.color.toastFailedBackground));
                        ToastMessage.show();
                    }
                }else{
                    Toast ToastMessage = Toast.makeText(getApplicationContext(), "Błąd Serwera", Toast.LENGTH_SHORT);
                    View toastView = ToastMessage.getView();
                    toastView.setBackgroundColor(getResources().getColor(R.color.toastFailedBackground));
                    ToastMessage.show();
                }
            }
        });

        profileViewModel.getBlockUserResult().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User userResponse) {
                if(userResponse!=null) {
                    Log.v(TAG, userResponse.toString());
                    if (!userResponse.isValid()) {
                        profileViewModel.setUser(userResponse);
                        Toast ToastMessage = Toast.makeText(getApplicationContext(), "Profil Zablokowany", Toast.LENGTH_SHORT);
                        View toastView = ToastMessage.getView();
                        toastView.setBackgroundColor(getResources().getColor(R.color.toastBackground));
                        ToastMessage.show();
                        loadViewValues();
                    }else{
                        Toast ToastMessage = Toast.makeText(getApplicationContext(), "Błąd Uprawnień", Toast.LENGTH_SHORT);
                        View toastView = ToastMessage.getView();
                        toastView.setBackgroundColor(getResources().getColor(R.color.toastFailedBackground));
                        ToastMessage.show();
                    }
                }else{
                    Toast ToastMessage = Toast.makeText(getApplicationContext(), "Błąd Serwera", Toast.LENGTH_SHORT);
                    View toastView = ToastMessage.getView();
                    toastView.setBackgroundColor(getResources().getColor(R.color.toastFailedBackground));
                    ToastMessage.show();
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
        if(profileViewModel.getUser().isValid()){
            nameTextView.setText(user.getName()+" "+user.getSurname());
        }else{
            nameTextView.setText(user.getName()+" "+user.getSurname()+" UŻYTKOWNIK ZABLOKOWANY");
        }

        if(profileViewModel.isAdmin()) {
            adminDeleteButton.setVisibility(View.VISIBLE);
            if (profileViewModel.getUser().isValid()) {
                adminBlockButton.setVisibility(View.VISIBLE);
                adminBlockButton.setText("Zablokuj");
            } else {
                adminBlockButton.setText("Odblokuj");
                adminBlockButton.setVisibility(View.VISIBLE);
            }
        }

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

        }else if(profileViewModel.getSendIvitation()){
            Log.v("TAG", "WYSYLam dodaj dfreinds");
            friendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    profileViewModel.confirmFriends();
                }
            });
        }else {
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