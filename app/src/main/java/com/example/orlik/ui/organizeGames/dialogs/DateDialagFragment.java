package com.example.orlik.ui.organizeGames.dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.orlik.R;
import com.example.orlik.ui.organizeGames.OrganizeViewModel;
import com.example.orlik.ui.organizeGames.OrganizeViewModelFactory;

import java.util.Calendar;
import java.util.TimeZone;

public class DateDialagFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    private OrganizeViewModel organizeViewModel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        organizeViewModel= new ViewModelProvider(requireActivity(), new OrganizeViewModelFactory())
                .get(OrganizeViewModel.class);
        final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Poland"));
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),R.style.my_dialog_theme,this, year,month,day);

        return datePickerDialog;
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        TextView dateTextView = getActivity().findViewById(R.id.organize_gameDate_TextView);
        String gameDate=day+"/"+(month+1)+"/"+year;
        dateTextView.setText(gameDate);
        organizeViewModel.setGameDate(gameDate);
    }
}
