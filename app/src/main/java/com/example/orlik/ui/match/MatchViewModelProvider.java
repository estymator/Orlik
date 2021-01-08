package com.example.orlik.ui.match;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MatchViewModelProvider implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MatchViewModel.class)){
            return (T) new MatchViewModel();
        }else{
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
