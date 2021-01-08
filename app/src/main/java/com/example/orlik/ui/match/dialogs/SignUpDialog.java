package com.example.orlik.ui.match.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.orlik.R;
import com.example.orlik.ui.match.MatchViewModel;
import com.example.orlik.ui.match.MatchViewModelProvider;

public class SignUpDialog extends DialogFragment {

    private MatchViewModel matchViewModel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        matchViewModel = new ViewModelProvider(requireActivity(), new MatchViewModelProvider()).get(MatchViewModel.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Wybierz Zespół")
                .setItems(R.array.teams_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        matchViewModel.signUpPlayer(i+1);
                    }
                });
        return builder.create();
    }
}
