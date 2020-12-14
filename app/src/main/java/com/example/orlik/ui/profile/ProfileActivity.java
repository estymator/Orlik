package com.example.orlik.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.orlik.R;
import com.example.orlik.ui.Basic.BasicActivity;
import com.example.orlik.ui.main.MainViewModel;
import com.example.orlik.ui.main.MainViewModelFactory;

public class ProfileActivity extends BasicActivity {
    final static private String TAG="ProfileActivityTAG";
    private ProfileViewModel profileViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.v(TAG,"OnCreate");
        profileViewModel =  new ViewModelProvider(this).get(ProfileViewModel.class);
    }
}