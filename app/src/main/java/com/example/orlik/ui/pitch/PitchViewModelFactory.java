package com.example.orlik.ui.pitch;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.orlik.ui.organizeGames.OrganizeViewModel;

public class PitchViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(PitchViewModel.class)){
            return (T) new PitchViewModel();
        }else
        {
            throw new IllegalArgumentException("Unknow ViewModelClass");
        }
    }
}
