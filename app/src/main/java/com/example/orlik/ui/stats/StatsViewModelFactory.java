package com.example.orlik.ui.stats;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class StatsViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(StatsViewModel.class)){
            return (T) new StatsViewModel();
        }else
        {
            throw new IllegalArgumentException("UnknowViewModelClass");
        }
    }
}
