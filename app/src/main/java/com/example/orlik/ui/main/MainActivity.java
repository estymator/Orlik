package com.example.orlik.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.orlik.R;
import com.example.orlik.data.adapters.GamesResultAdapter;
import com.example.orlik.data.model.Notification;
import com.example.orlik.data.model.Session;
import com.example.orlik.data.model.User;
import com.example.orlik.data.model.dto.GameDTO;
import com.example.orlik.data.model.dto.NotificationDTO;
import com.example.orlik.ui.Basic.BasicActivity;
import com.example.orlik.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends BasicActivity {
    private Session session;
    private User loggedInUser;
    private static final String TAG="MainActivity";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE=1;
    private MainViewModel mainViewModel;
    private boolean loginRedirectFlag=false; //check if already redirect to login
    BottomNavigationView bottomNavigationView;
    Button logoutButton, searchUsersButton;
    TextView nameTextView, gamesTextView, wonTextView, trustRateTextView, wonRatioTextView, loginTextView, emptyNotificationsTextView, notificationsTextView;
    RecyclerView friendsRecyclerView, searchUserRecyclerView, attendGamesRecyclerView, organizeGamesRecyclerView, notificationsRecyclerView;
    EditText searchUsersEditText;
    LinearLayout searchUsersLinearLayout;

    //TODO do not return currently logged in user as a search result

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG,"OnCreate");
        session = new Session(this);
        setContentView(R.layout.activity_main);
        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.INTERNET
        });

        mainViewModel =  new ViewModelProvider(this, new MainViewModelFactory()).get(MainViewModel.class);
        loginTextView = findViewById(R.id.main_login_TextView);
        nameTextView=findViewById(R.id.main_name_TextView);
        gamesTextView=findViewById(R.id.main_games_TextView);
        wonTextView=findViewById(R.id.main_won_TextView);
        trustRateTextView=findViewById(R.id.main_trustRate_TextView);
        wonRatioTextView=findViewById(R.id.main_wonRatio_TextView);

        logoutButton = (Button) findViewById(R.id.main_logout_button);
        searchUsersButton = (Button) findViewById(R.id.main_searchFriends_button);

        searchUsersEditText = (EditText) findViewById(R.id.main_searchFriends_EditText);

        friendsRecyclerView=(RecyclerView) findViewById(R.id.main_friends_recyclerView);
        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        friendsRecyclerView.setAdapter(mainViewModel.getFriendsAdapter());


        searchUserRecyclerView=(RecyclerView) findViewById(R.id.main_searchUsers_recyclerView);
        searchUserRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        searchUserRecyclerView.setAdapter(mainViewModel.getSearchUsersAdapter());

        attendGamesRecyclerView=(RecyclerView) findViewById(R.id.main_attendGames_recycylerView);
        attendGamesRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        attendGamesRecyclerView.setAdapter(mainViewModel.getAttendGamesAdapter());

        organizeGamesRecyclerView=(RecyclerView) findViewById(R.id.main_organizedGames_recycylerView);
        organizeGamesRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        organizeGamesRecyclerView.setAdapter(mainViewModel.getOrganizeGamesAdapter());

        notificationsRecyclerView=(RecyclerView) findViewById(R.id.main_notifications_recyclerView);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        notificationsRecyclerView.setAdapter(mainViewModel.getNotificationAdapter());

        emptyNotificationsTextView = (TextView) findViewById(R.id.main_emptyNotifications_textView);

        bottomNavigationView = findViewById(R.id.main_toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemReselectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_item_profile);
        checkAdmin(bottomNavigationView);

        searchUsersLinearLayout = (LinearLayout) findViewById(R.id.main_searchUsers_linearLayout);




        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutButton.setEnabled(false);
                mainViewModel.logout();
            }
        });

        searchUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = (String) searchUsersEditText.getText().toString();
                if(query.length()>0){
                    mainViewModel.findUsers(query);
                }
            }
        });




        mainViewModel.getLogoutResult().observe(this, new Observer<LogoutResult>() {
            @Override
            public void onChanged(@Nullable LogoutResult logoutResult) {
                if (logoutResult == null) {
                    return;
                }
                if (logoutResult.getResult().equals("Wylogowano")) {

                    mainViewModel.clearUser();
                    logoutButton.setEnabled(true);
                    try{
                        session.logout();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);

                    }catch (Exception e)
                    {
                        Log.v(TAG,"Błąd usuniecia danych z sesji");
                    }
                }else{
                    Toast.makeText(getApplicationContext(), logoutResult.getResult(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mainViewModel.getFriends().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> friends) {
                if(friends.size()>0){
                    ArrayList<User> bufor = mainViewModel.getFriendsAdapterDataSet();
                    bufor.clear();
                    bufor.addAll(friends);
                    mainViewModel.getFriendsAdapter().notifyDataSetChanged();
                }else{
                    mainViewModel.getFriendsAdapterDataSet().clear();
                    mainViewModel.getFriendsAdapter().notifyDataSetChanged();
                }
            }
        });

        mainViewModel.getLoggedInUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user==null){
                   //after logout
                }else if(user.getFetchError().isEmpty()){
                    session.setUser(user);
                    loadViewValues(user); //TODO after change login user if new user dont have approaching games the old ones stay visible
                }else
                {
                    mainViewModel.logout();
                }
            }
        });

        mainViewModel.getSearchUserResult().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> userArrayList) {
                if(userArrayList.size()>0){
                    searchUsersLinearLayout.setVisibility(View.VISIBLE);
                    ArrayList<User> bufor = mainViewModel.getSearchUsersAdapterDataSet();
                    bufor.clear();
                    bufor.addAll(userArrayList);
                    mainViewModel.getSearchUsersAdapter().notifyDataSetChanged();
                }else{
                    mainViewModel.getSearchUsersAdapterDataSet().clear();
                    mainViewModel.getSearchUsersAdapter().notifyDataSetChanged();
                    searchUsersLinearLayout.setVisibility(View.GONE);
                }
            }
        });

        mainViewModel.getDeleteNotificationResult().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean!=null)
                {
                    if(aBoolean){
                        mainViewModel.reloadNotifications();
                    }
                }
            }
        });

        mainViewModel.getGetOrganisedGameResult().observe(this, new Observer<ArrayList<GameDTO>>() {
            @Override
            public void onChanged(ArrayList<GameDTO> games) {
                if(games.size()>0){
                    mainViewModel.getOrganizeGamesAdapterDataSet().clear();
                    mainViewModel.getOrganizeGamesAdapterDataSet().addAll(games);
                    mainViewModel.getOrganizeGamesAdapter().notifyDataSetChanged();
                }else{
                    mainViewModel.getOrganizeGamesAdapterDataSet().clear();
                    mainViewModel.getOrganizeGamesAdapter().notifyDataSetChanged();
                }
            }
        });

        mainViewModel.getGetAttendGameResult().observe(this, new Observer<ArrayList<GameDTO>>() {
            @Override
            public void onChanged(ArrayList<GameDTO> games) {
                if(games.size()>0){
                    mainViewModel.getAttendGamesAdapterDataSet().clear();
                    mainViewModel.getAttendGamesAdapterDataSet().addAll(games);
                    mainViewModel.getAttendGamesAdapter().notifyDataSetChanged();
                }else{
                    mainViewModel.getAttendGamesAdapterDataSet().clear();
                    mainViewModel.getAttendGamesAdapter().notifyDataSetChanged();
                }
            }
        });

        mainViewModel.getNotificationsResult().observe(this, new Observer<ArrayList<NotificationDTO>>() {
            @Override
            public void onChanged(ArrayList<NotificationDTO> notifications) {
                if(notifications!=null){
                    if(notifications.size()>0){
                        mainViewModel.getNotificationsDataSet().clear();
                        mainViewModel.getNotificationsDataSet().addAll(notifications);
                        mainViewModel.getNotificationAdapter().setMainViewModel(mainViewModel);
                        mainViewModel.getNotificationAdapter().notifyDataSetChanged();
                        emptyNotificationsTextView.setVisibility(View.GONE);
                    }else{
                        emptyNotificationsTextView.setVisibility(View.VISIBLE);
                        mainViewModel.getNotificationsDataSet().clear();
                        mainViewModel.getNotificationAdapter().notifyDataSetChanged();

                    }
                }
            }
        });


        if(session.getCredentials()!=null) {
            if (session.getUser() != null) {
                mainViewModel.getLoggedInUser().setValue(session.getUser());
            } else {
                mainViewModel.getUserInfo();
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "OnPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "OnDestroy");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.v(TAG,"onStop");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.v(TAG,"onRestart");
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        Log.v(TAG, "On Start");
        session= new Session(getApplicationContext());
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.v(TAG, "onResume");

        session= new Session(getApplicationContext()); //TODO: check if session can notice all changes done after session initalization
        if(mainViewModel.getLoggedInUser().getValue()==null){

            if(session.getCredentials()!=null) {
                if (session.getUser() != null) {
                    checkAdmin(bottomNavigationView);
                    mainViewModel.getLoggedInUser().setValue(session.getUser());
                    mainViewModel.loadGames();
                } else {
                    mainViewModel.getUserInfo();
                }
            }
        }

    }

    /**
     * function load to view objects user info
     */
    private void loadViewValues(User user){
        float winRatio;
        if(user.getTotalGames()!=0){
            winRatio=(float)user.getWinGames()/user.getTotalGames();
        }else{
            winRatio=0;
        }

        nameTextView.setText(user.getName()+" "+user.getSurname());
        loginTextView.setText("Login: "+user.getLogin());
        gamesTextView.setText("Liczba rozegranych gier: "+user.getTotalGames());
        wonTextView.setText("Liczba wygranych gier: "+user.getWinGames());
        wonRatioTextView.setText("Współczynnik wygranych: "+winRatio);
        trustRateTextView.setText("Współczynnik zaufania: "+user.getTrustRate());
        mainViewModel.loadGames();
    }


    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
}