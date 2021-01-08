package com.example.orlik.ui.profile.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.orlik.ui.profile.ProfileViewModel;

public class BlockUserDialog extends DialogFragment {
    private ProfileViewModel profileViewModel;
    private String title;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if(profileViewModel.getUser().isValid()){
            title="Zablokowac użytkownika?";
        }else{
            title="Odblokuj użytkownika";
        }
        builder.setMessage(title)
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(profileViewModel.getUser().isValid()){
                            profileViewModel.blockUser();
                        }else{
                            profileViewModel.unbanUser();
                        }
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
