package com.example.orlik.ui.main;

import android.content.Intent;
import android.os.Bundle;

import com.example.orlik.R;
import com.example.orlik.data.model.Session;
import com.example.orlik.data.model.User;
import com.example.orlik.ui.Basic.BasicActivity;
import com.example.orlik.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
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

import java.util.ArrayList;

public class MainActivity extends BasicActivity {
    private Session session;
    private User loggedInUser;
    private static final String TAG="MainActivity";
    private MainViewModel mainViewModel;
    private boolean loginRedirectFlag=false; //check if already redirect to login
    BottomNavigationView bottomNavigationView;
    Button logoutButton, searchUsersButton;
    Button listUsersButton;
    TextView nameTextView, gamesTextView, wonTextView, trustRateTextView, wonRatioTextView, loginTextView;
    RecyclerView friendsRecyclerView, searchUserRecyclerView;
    EditText searchUsersEditText;
    LinearLayout searchUsersLinearLayout;
    ScrollView friendsScrollView;
    //TODO do not return currently logged in user as a search result

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG,"OnCreate");
        session = new Session(this);
        setContentView(R.layout.activity_main);
        mainViewModel =  new ViewModelProvider(this, new MainViewModelFactory()).get(MainViewModel.class);

        loginTextView = findViewById(R.id.main_login_TextView);
        listUsersButton=findViewById(R.id.main_list_users_button);
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

        bottomNavigationView = findViewById(R.id.main_toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemReselectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_item_profile);

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



        listUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.listUsers();
            }
        });

        mainViewModel.getLogoutResult().observe(this, new Observer<LogoutResult>() {
            @Override
            public void onChanged(@Nullable LogoutResult logoutResult) {
                if (logoutResult == null) {
                    return;
                }
                if (logoutResult.getResult() == "Wylogowano") {

                    mainViewModel.clearUser();
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
                    loadViewValues(user);
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
                    mainViewModel.getLoggedInUser().setValue(session.getUser());

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

        mainViewModel.loadFriends();
    }

}