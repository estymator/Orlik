package com.example.orlik.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.orlik.Network.RetrofitServiceGenerator;
import com.example.orlik.R;
import com.example.orlik.data.model.Session;
import com.example.orlik.data.model.User;
import com.example.orlik.ui.Basic.BasicActivity;
import com.example.orlik.ui.games.GamesActivity;
import com.example.orlik.ui.login.LoginActivity;
import com.example.orlik.ui.organizeGames.OrganizeActivity;
import com.example.orlik.ui.settings.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import okhttp3.Interceptor;

public class MainActivity extends BasicActivity {
    private Session session;
    private User loggedInUser;
    private static final String TAG="MainActivity";
    private MainViewModel mainViewModel;
    private boolean loginRedirectFlag=false; //check if already redirect to login
    BottomNavigationView bottomNavigationView;
    Button logoutButton;
    Button listUsersButton;
    TextView nameTextView, gamesTextView, wonTextView, trustRateTextView, wonRatioTextView, loginTextView;
    ListView friendsListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG,"OnCreate");
        session = new Session(this);
        setContentView(R.layout.activity_main);
        mainViewModel =  new ViewModelProvider(this, new MainViewModelFactory()).get(MainViewModel.class);

        loginTextView = findViewById(R.id.main_login_TextView);
        logoutButton = findViewById(R.id.main_logout_button);
        listUsersButton=findViewById(R.id.main_list_users_button);
        nameTextView=findViewById(R.id.main_name_TextView);
        gamesTextView=findViewById(R.id.main_games_TextView);
        wonTextView=findViewById(R.id.main_won_TextView);
        trustRateTextView=findViewById(R.id.main_trustRate_TextView);
        wonRatioTextView=findViewById(R.id.main_wonRatio_TextView);

        friendsListView=(ListView) findViewById(R.id.main_friends_ListView);

        bottomNavigationView = findViewById(R.id.main_toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemReselectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_item_profile);



        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutButton.setEnabled(false);

                mainViewModel.logout();
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
                Log.v(TAG, friends.toString());
                ArrayAdapter<String> friendsListAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, mainViewModel.getFriendsLogin());
                friendsListView.setAdapter(friendsListAdapter);

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

        friendsListView.setOnItemClickListener(mainViewModel.getFriendsListListener());


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