package com.example.orlik.ui.profile.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.orlik.ui.organizeGames.OrganizeViewModel;
import com.example.orlik.ui.organizeGames.OrganizeViewModelFactory;
import com.example.orlik.ui.profile.ProfileViewModel;

public class RemoveUserDialog extends DialogFragment {
    private ProfileViewModel profileViewModel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Usunąć użytkownika?")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        profileViewModel.deleteUser();
                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }
}
