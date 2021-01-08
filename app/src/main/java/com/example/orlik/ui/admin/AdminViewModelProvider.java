package com.example.orlik.ui.admin;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AdminViewModelProvider implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(AdminViewModel.class)){
            return (T) new AdminViewModel();
        }else{
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
